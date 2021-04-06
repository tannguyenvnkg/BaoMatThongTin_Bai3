package RSA;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.random;
import static java.lang.System.in;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// <editor-fold defaultstate="collapsed" desc=" Constructor ">


        // </editor-fold>
/**
 *
 * @author tanng
 */
public class RSA {
    
    
    int primeSize;
        
    BigInteger p,q;
    
    BigInteger N;
    
    BigInteger r;
    BigInteger E,D;
    
    // <editor-fold defaultstate="collapsed" desc=" Constructor ">
    public RSA(){
        
    }
    public RSA(int primeSize){
        this.primeSize = primeSize;
        generatePrimeNumbers();
        generatePublicPrivateKeys();
    }

//    public RSA(int primeSize, BigInteger p, BigInteger q, BigInteger N, BigInteger r, BigInteger E, BigInteger D) {
//        this.primeSize = primeSize;
//        this.p = p;
//        this.q = q;
//        this.N = N;
//        this.r = r;
//        this.E = E;
//        this.D = D;   
//    }

    public int getPrimeSize() {
        return primeSize;
    }

    public BigInteger getP() {
        return (p);
    }

    public BigInteger getQ() {
        return (q);
    }

    public BigInteger getN() {
        return (N);
    }

    public BigInteger getR() {
        return (r);
    }

    public BigInteger getE() {
        return (E);
    }

    public BigInteger getD() {
        return (D);
    }
    public void setPrimeSize(int primeSize) {
        this.primeSize = primeSize;
    }

    public void setP(BigInteger p) {
        this.p = p;
    }

    public void setQ(BigInteger q) {
        this.q = q;
    }

    public void setN(BigInteger N) {
        this.N = N;
    }

    public void setR(BigInteger r) {
        this.r = r;
    }

    public void setE(BigInteger E) {
        this.E = E;
    }

    public void setD(BigInteger D) {
        this.D = D;
    }
    
        // </editor-fold>

    public void generatePrimeNumbers(){
        //double random = (double)(Math.random()*101);
        p = BigInteger.probablePrime(primeSize /2, new Random());
        
        do {
            q = BigInteger.probablePrime(primeSize / 2 , new Random());
        } while (q.compareTo(p) == 0);
        
    }
    
    public void generatePublicPrivateKeys(){
        N = p.multiply(q);
        r = p.subtract(BigInteger.valueOf(1));
        r = r.multiply(q.subtract(BigInteger.valueOf(1)));
        
        do {
            E = new BigInteger(2*primeSize, new Random());
        } while ((E.compareTo(r) != -1 ) || (E.gcd(r).compareTo(BigInteger.valueOf(1)) != 0 ));
        
        D = E.modInverse(r);
    }
    
    public BigInteger[] encrypt(String message){
        int i;
        byte[] temp = new byte[1];
        byte[] digits = message.getBytes();
        
        BigInteger[] bigdigits = new BigInteger[digits.length];
        
        for ( i = 0; i < bigdigits.length; i++) {
            temp[0] = digits[i];
            bigdigits[i] = new BigInteger(temp);
        }
        
        BigInteger[] encrypted = new BigInteger[bigdigits.length];
        
        for (i = 0; i < bigdigits.length; i++) {
            encrypted[i] = bigdigits[i].modPow(E, N);
            
            
        }
        return encrypted;
    }
    
    
    public BigInteger[] encrypt(String message,BigInteger userD, BigInteger userN){
        int i;
        byte[] temp = new byte[1];
        byte[] digits = message.getBytes();
        
        BigInteger[] bigdigits = new BigInteger[digits.length];
        
        for ( i = 0; i < bigdigits.length; i++) {
            temp[0] = digits[i];
            bigdigits[i] = new BigInteger(temp);
        }
        
        BigInteger[] encrypted = new BigInteger[bigdigits.length];
        
        for (i = 0; i < bigdigits.length; i++) {
            encrypted[i] = bigdigits[i].modPow(userD, userN);
        }
        return encrypted;
    }
    
    public String decrypt(BigInteger[] encrypted, BigInteger D, BigInteger N){
        int i;
        BigInteger[] decrypted = new BigInteger[encrypted.length];
        
        for (i = 0; i < decrypted.length; i++) {
            decrypted[i] = encrypted[i].modPow(D, N);
        }
        
        char[] charArray = new char[decrypted.length];
        
        for (i = 0; i < charArray.length; i++) {
            charArray[i] = (char) (decrypted[i].intValue());
        }
        return (new String(charArray));
    }
    
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int primeSize = 8;
        
        RSA rsa = new RSA(primeSize);
        
        System.out.println("Key Size: [" + primeSize + "]");
        System.out.println("");
        
        System.out.println("Generated prime numbers p and q");
        System.out.println("p: [" + rsa.getP().toString(16).toUpperCase() + "]");
        System.out.println("q: [" + rsa.getQ().toString(16).toUpperCase() + "]");
        System.out.println("");
        
        System.out.println("The public key is the pair (N,E) which will be published");
        System.out.println("N: [" + rsa.getN().toString(16).toUpperCase() + "]");
        System.out.println("E: [" + rsa.getE().toString(16).toUpperCase() + "]");
        System.out.println("");
        
        System.out.println("The private key is pair (N,D) which will be kept private.");
        System.out.println("N: [" + rsa.getN().toString(16).toUpperCase() + "]");
        System.out.println("E: [" + rsa.getE().toString(16).toUpperCase() + "]");
        System.out.println("");
        
        System.out.println("please enter message (plaintext): ");
        String plaintext = (new BufferedReader(new InputStreamReader(System.in))).readLine();
        //String plaintext = in.nextLine();
        System.out.println("");
        
        BigInteger[] ciphertext = rsa.encrypt(plaintext);
        
        System.out.print("CipherText: [");
        for (int i = 0; i < ciphertext.length; i++) {
            System.out.print(ciphertext[i].toString(16).toUpperCase());
            
            if(i != ciphertext.length - 1) System.out.println("");
        }
        System.out.println("]");
        System.out.println("");
        
        RSA rsa1 = new RSA(8);
        
        String recoveredPlaintext = rsa1.decrypt(ciphertext, rsa.getD(), rsa.getN());
        
        System.out.println("Recovered plaintext: [" + recoveredPlaintext + "]");
    }
}






