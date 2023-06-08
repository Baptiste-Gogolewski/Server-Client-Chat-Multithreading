package source;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHash
{
    private String Password;
    private String Hash;

    public PasswordHash(String password)
    {
        this.Password = password;

        try
        {
            // Utilisation du systeme de chiffrement SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(this.Password.getBytes());

            // Convertir les octets hashés en représentation hexadécimale
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            Hash = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }

    public String GetHash()
    {
        return this.Hash;
    }


    public static void main(String[] args)
    {
        PasswordHash passwordHash = new PasswordHash("123456");
        String h = passwordHash.GetHash();

        System.out.println(h);
    }
}
