/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSANangCao;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.interfaces.RSAPublicKey;

/**
 *
 * @author tanng
 */
public class Enc {
    public static void Enckethua() throws FileNotFoundException, IOException, ClassNotFoundException{
        String s= "Hello World";
        
        FileInputStream a= new FileInputStream("D:\\Skey_RSA_pub.dat");
        ObjectInputStream w = new ObjectInputStream(a);
        
        RSAPublicKey pbk = (RSAPublicKey)w.readObject();
        BigInteger r = pbk.getPublicExponent();
        BigInteger g = pbk.getModulus();
        System.out.println("e = " + r);
        System.out.println("n = " + g);
        byte ptext[] = s.getBytes("UTF8");
        BigInteger o = new BigInteger(ptext);
        BigInteger u = o.modPow(r, g);
        System.out.println("c = " + u);
        String cs = u.toString();
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:\\Enc_RSA.dat")));
        out.write(cs,0,cs.length());
        out.close();
    }
}
