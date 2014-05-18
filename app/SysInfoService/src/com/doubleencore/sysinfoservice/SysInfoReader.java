package com.doubleencore.sysinfoservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.LocalSocket;
import android.net.LocalSocketAddress;
import android.util.Slog;

public class SysInfoReader {
	private static final String TAG = "SysInfoService";
	private static final boolean LOCAL_DEBUG = false;
	
	private InputStream mIn;
	private OutputStream mOut;
	
	private LocalSocket mSocket;
	
	byte buf[] = new byte[1024];
    int buflen = 0;
	
	private boolean connect() {
		if (mSocket != null) {
			return true;
		}
		
		try {
            mSocket = new LocalSocket();
            LocalSocketAddress address = new LocalSocketAddress("sysinfo",
                    LocalSocketAddress.Namespace.RESERVED);
            
            mSocket.connect(address);
            mIn = mSocket.getInputStream();
            mOut = mSocket.getOutputStream();
		} catch (IOException e) {
			disconnect();
			return false;
		}		
		return true;
	}
	
	private void disconnect() {
        try {
            if (mSocket != null)
                mSocket.close();
        } catch (IOException ex) {
        }
        try {
            if (mIn != null)
                mIn.close();
        } catch (IOException ex) {
        }
        try {
            if (mOut != null)
                mOut.close();
        } catch (IOException ex) {
        }
        mSocket = null;
        mIn = null;
        mOut = null;
	}
	
    private boolean readBytes(byte buffer[], int len) {
        int off = 0, count;
        if (len < 0)
            return false;
        while (off != len) {
            try {
                count = mIn.read(buffer, off, len - off);
                if (count <= 0) {
                    Slog.e(TAG, "read error " + count);
                    break;
                }
                off += count;
            } catch (IOException ex) {
                Slog.e(TAG, "read exception");
                break;
            }
        }
        if (LOCAL_DEBUG) {
            Slog.i(TAG, "read " + len + " bytes");
        }
        if (off == len)
            return true;
        disconnect();
        return false;
    }
    
    private boolean readReply() {
        int len;
        buflen = 0;
        if (!readBytes(buf, 2))
            return false;
        len = (((int) buf[0]) & 0xff) | ((((int) buf[1]) & 0xff) << 8);
        if ((len < 1) || (len > 1024)) {
            Slog.e(TAG, "invalid reply length (" + len + ")");
            disconnect();
            return false;
        }
        if (!readBytes(buf, len))
            return false;
        buflen = len;
        return true;
    }
    
    private boolean writeCommand(String _cmd) {
        byte[] cmd = _cmd.getBytes();
        int len = cmd.length;
        if ((len < 1) || (len > 1024))
            return false;
        buf[0] = (byte) (len & 0xff);
        buf[1] = (byte) ((len >> 8) & 0xff);
        try {
            mOut.write(buf, 0, 2);
            mOut.write(cmd, 0, len);
        } catch (IOException ex) {
            Slog.e(TAG, "write error");
            disconnect();
            return false;
        }
        return true;
    }
    
    private synchronized String transaction(String cmd) {
        if (!connect()) {
            Slog.e(TAG, "connection failed");
            return "-1";
        }
        if (!writeCommand(cmd)) {
            /*
             * If sysinfo died and restarted in the background (unlikely but
             * possible) we'll fail on the next write (this one). Try to
             * reconnect and write the command one more time before giving up.
             */
            Slog.e(TAG, "write command failed? reconnect!");
            if (!connect() || !writeCommand(cmd)) {
                return "-1";
            }
        }
        if (LOCAL_DEBUG) {
            Slog.i(TAG, "send: '" + cmd + "'");
        }
        if (readReply()) {
            String s = new String(buf, 0, buflen);
            if (LOCAL_DEBUG) {
                Slog.i(TAG, "recv: '" + s + "'");
            }
            return s;
        } else {
            if (LOCAL_DEBUG) {
                Slog.i(TAG, "fail");
            }
            return "-1";
        }
    }
    
    private String execute(String cmd) {
        String reply = transaction(cmd);
        return reply;
    }
    
    public String readCpuInfo() {
    	return execute("cpu");
    }
    
    public String readMemInfo() {
    	return execute("memory");
    }
    
    public String readCommandline() {
    	return execute("cmdline");
    }
}
