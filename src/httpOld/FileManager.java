package httpOld;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class FileManager {
    private String path;

    private static ConcurrentHashMap<String, byte[]> map = new ConcurrentHashMap<String, byte[]>();
   // время создания кэша
    private static long startMap=System.currentTimeMillis();
     //класс считает врямя жизни кеша
    TimeManager tm =new TimeManager();

    public FileManager(String path) {
        if (path.endsWith("/") || path.endsWith("\\"))
        	path = path.substring(0, path.length() - 1);
        this.path = path;
    }
    public byte[] get(String url) {
        try {
            int r=tm.getT(startMap);
            if (map.containsKey(url)&&(r==1)) {
                return map.get(url);
            } else {
                String fullPath = path + url.replace('/', '\\');
                byte[] buf;
                RandomAccessFile f = new RandomAccessFile(fullPath, "r");
                try {
                    buf = new byte[(int)f.length()];
                    f.read(buf, 0, buf.length);
                } finally {
                    f.close();
                }
                map.put(url, buf);
                // фиксирую время создания кеша
                startMap=System.currentTimeMillis();
                return buf;
            }
        } catch (IOException ex) {
            return null;
        }
    }
}
