package com.github.alphastuff.amazor;

import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebUtil {
    public static String readRequestFromUrl(String web) throws IOException {
        URL url = new URL(web);
        @SuppressWarnings("resource")
        Scanner sc = new Scanner(url.openStream());
        StringBuilder sb = new StringBuilder();
        while(sc.hasNext()) {
            sb.append(sc.next());
        }
        return sb.toString();
    }

    public static String getDog() throws IOException {
        return JsonParser.parseString(readRequestFromUrl("https://shibe.online/api/shibes?count=1")).getAsJsonArray().get(0).getAsString();
    }

    public static File readImage(String web) throws IOException {
        URL url = new URL(web);
        InputStream in = new BufferedInputStream(url.openStream());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();
        byte[] response = out.toByteArray();

        File file = new File(System.getProperty("user.dir")+"//temp-image.png");
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(response);
        fos.close();

        return file;
    }
}
