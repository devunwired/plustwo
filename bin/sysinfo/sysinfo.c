#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
       
#include <cutils/sockets.h>
#include <cutils/log.h>

#define SOCKET_PATH "sysinfo"
#define CPU_PATH "/proc/cpuinfo"
#define MEM_PATH "/proc/meminfo"
#define CMD_PATH "/proc/cmdline"

#define BUFFER_MAX 1024 /* buffer for input commands */
#define REPLY_MAX 1024

static int readx(int s, void *_buf, int count)
{
    char *buf = _buf;
    int n = 0, r;
    if (count < 0) return -1;
    while (n < count) {
        r = read(s, buf + n, count - n);
        if (r < 0) {
            if (errno == EINTR) continue;
            ALOGE("read error: %s\n", strerror(errno));
            return -1;
        }
        if (r == 0) {
            ALOGE("eof\n");
            return -1; /* EOF */
        }
        n += r;
    }
    return 0;
}

static int writex(int s, const void *_buf, int count)
{
    const char *buf = _buf;
    int n = 0, r;
    if (count < 0) return -1;
    while (n < count) {
        r = write(s, buf + n, count - n);
        if (r < 0) {
            if (errno == EINTR) continue;
            ALOGE("write error: %s\n", strerror(errno));
            return -1;
        }
        n += r;
    }
    return 0;
}

/* Tokenize the command buffer, locate a matching command,
 * ensure that the required number of arguments are provided,
 * call the function(), return the result.
 */
static int execute(int s, char cmd[BUFFER_MAX])
{
    char reply[REPLY_MAX];
    unsigned n = 0;
    unsigned short count;
    int ret = -1;

    /* default reply is "" */
    reply[0] = 0;

    //Check which command was received
    if (!strcmp("cpu", cmd)) {
        FILE *f = fopen(CPU_PATH, "rb");

        count = fread(reply, 1, REPLY_MAX, f);
        fclose(f);

        reply[count] = 0;
        
        goto done;
    }
    if (!strcmp("memory", cmd)) {
        FILE *f = fopen(MEM_PATH, "rb");

        count = fread(reply, 1, REPLY_MAX, f);
        fclose(f);

        reply[count] = 0;

        goto done;
    }
    if (!strcmp("cmdline", cmd)) {
        FILE *f = fopen(CMD_PATH, "rb");

        count = fread(reply, 1, REPLY_MAX, f);
        fclose(f);

        reply[count] = 0;

        goto done;
    }

    ALOGE("unsupported command '%s'\n", cmd);

done:
    ALOGI("Read %d bytes\n", count);
    if (reply[0]) {
        n = snprintf(cmd, BUFFER_MAX, "%s", reply);
    } else {
        n = snprintf(cmd, BUFFER_MAX, "%d", ret);
    }
    if (n > BUFFER_MAX) n = BUFFER_MAX;
    count = n;

    ALOGI("reply: '%s'\n", cmd);
    if (writex(s, &count, sizeof(count))) return -1;
    if (writex(s, cmd, count)) return -1;
    return 0;
}

int main(const int argc, const char *argv[]) {
    char buf[BUFFER_MAX];
    struct sockaddr addr;
    socklen_t alen;
    int lsocket, s, count;

    ALOGI("Running sysinfo daemon\n");
    
    //Connect to device socket
    lsocket = android_get_control_socket(SOCKET_PATH);
    if (lsocket < 0) {
        ALOGE("Failed to get socket from environment: %s\n", strerror(errno));
        exit(1);
    }
    if (listen(lsocket, 5)) {
        ALOGE("Listen on socket failed: %s\n", strerror(errno));
        exit(1);
    }
    fcntl(lsocket, F_SETFD, FD_CLOEXEC);
    
    for (;;) {
        alen = sizeof(addr);
        s = accept(lsocket, &addr, &alen);
        if (s < 0) {
            ALOGE("Accept failed: %s\n", strerror(errno));
            continue;
        }
        fcntl(s, F_SETFD, FD_CLOEXEC);

        ALOGI("new connection\n");
        for (;;) {
            unsigned short count;
            if (readx(s, &count, sizeof(count))) {
                ALOGE("failed to read size\n");
                break;
            }
            if ((count < 1) || (count >= BUFFER_MAX)) {
                ALOGE("invalid size %d\n", count);
                break;
            }
            if (readx(s, buf, count)) {
                ALOGE("failed to read command\n");
                break;
            }
            buf[count] = 0;
            if (execute(s, buf)) break;
        }
        ALOGI("closing connection\n");
        close(s);
    }
    
    return 0;
}
