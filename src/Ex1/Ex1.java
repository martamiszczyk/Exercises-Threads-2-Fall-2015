/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Ex1
{

    public static void main(String[] args)
    {
      new testParallel().run();
//      new testSeqventiel().run();
        System.out.println("Avilable Processors: " + Runtime.getRuntime().availableProcessors());
    }

    public static class Thread1 extends Thread
    {

        private String URL;
        private byte[] bytes;
        private int sum;

        public Thread1(String URL)
        {
            this.URL = URL;
            bytes = getBytesFromUrl(URL);
        }

        public int resultat()
        {
            for (int i = 0; i < bytes.length; i++)
            {
                sum = sum + bytes[i];
            }
            return sum;
        }

        @Override
        public void run()
        {
            resultat();
        }

        public int getSum()
        {
            return sum;
        }
    }

    protected static byte[] getBytesFromUrl(String url)
    {
        ByteArrayOutputStream bis = new ByteArrayOutputStream();
        try
        {
            InputStream is = new URL(url).openStream();
            byte[] bytebuff = new byte[4096];
            int read;
            while ((read = is.read(bytebuff)) > 0)
            {
                bis.write(bytebuff, 0, read);
            }
        } catch (IOException ex)
        {
            System.out.println(ex);
        }
        return bis.toByteArray();
    }

    public static class testParallel
    {
        private int totalSum = 0;

        public void run()
        {
            long start2 = System.nanoTime();
            Thread1 t = new Thread1("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/fronter_big-logo.png");
            t.start();
            Thread1 t2 = new Thread1("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/folder-image-transp.png");
            t2.start();
            Thread1 t3 = new Thread1("https://fronter.com/volY12-cache/cache/img/design_images/Classic/other_images/button_bg.png");
            t3.start();

            try
            {
                t.join();
                t2.join();
                t3.join();
            } catch (InterruptedException ex)
            {
                Logger.getLogger(Ex1.class.getName()).log(Level.SEVERE, null, ex);
            }
            long end2 = System.nanoTime();
            System.out.println("Time Parallel: " + (end2 - start2));

            totalSum = totalSum + t.getSum();
            totalSum = totalSum + t2.getSum();
            totalSum = totalSum + t3.getSum();

            int sumTest = 127268;
            if (sumTest == totalSum)
            {
                System.out.println("Sucess");
                System.out.println(totalSum);
            } else
            {
                System.out.println("Fail");
                System.out.println(totalSum);
            }
        }
    }

    public static class testSeqventiel
    {

        public void run()
        {
            long start = System.nanoTime();

            Thread1 t1 = new Thread1("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/fronter_big-logo.png");
            Thread1 t2 = new Thread1("https://fronter.com/cphbusiness/design_images/images.php/Classic/login/folder-image-transp.png");
            Thread1 t3 = new Thread1("https://fronter.com/volY12-cache/cache/img/design_images/Classic/other_images/button_bg.png");

            t1.run();
            t2.run();
            t3.run();
            long end = System.nanoTime();
            System.out.println("Time Sequental: " + (end - start));
        }
    }
}