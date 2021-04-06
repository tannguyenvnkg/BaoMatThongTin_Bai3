package RSA;


import java.util.Scanner;
import java.math.BigInteger;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tanng
 */
// <editor-fold defaultstate="collapsed" desc=" Constructor ">


        // </editor-fold>
public class PwdEncryption {
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        
       // String nhash;
        BigInteger[] ciphertext = null;
        BigInteger n = null;
        BigInteger d = null;
        String password = "";
        System.out.println("Enter text to be encrypted: ");
        password = in.nextLine();

        System.out.println("Password(input) : " + password);

        RSA rsa = new RSA(8);
        n = rsa.getN();
        d = rsa.getD();
        ciphertext = rsa.encrypt(password);

        StringBuffer bf = new StringBuffer();
        for (int i = 0; i < ciphertext.length; i++) {
            bf.append(ciphertext[i].toString(16).toUpperCase());

            if( i != ciphertext.length - 1) System.out.print( " " );
        }
        
        String message = bf.toString();
        System.out.println("");
        System.out.println("Encrypted Message: " + message);
        
        String dhash = rsa.decrypt(ciphertext, d, n);
        System.out.println("");
        System.out.println("Decrypted Message: " + dhash);
    }
}
