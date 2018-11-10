package microservices;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class PaymentRESTImplTest {

    private final static PaymentRESTImpl a = new PaymentRESTImpl();
    @Test
    public void payment() throws ParseException {
        //date depassée
        /*
         {
            cardNumber: String,
            expirationDate: String,
            cardSecurityCode: String,
            commandN: string
        }
         */
        //numéro invalide < 16 ou > 16 char
        a.payment("{\"cardNumber\": \"443013326536721\", \"expirationDate\": \"09-20\", \"cardSecurityCode\": \"123\", \"commandN\": \"12345\"}");
        a.payment("{\"cardNumber\": \"44301332653672104\", \"expirationDate\": \"09-20\", \"cardSecurityCode\": \"234\", \"commandN\": \"12345\"}");
        //numéro invalide luhn
        a.payment("{\"cardNumber\": \"4430133265367211\", \"expirationDate\": \"09-20\", \"cardSecurityCode\": \"234\", \"commandN\": \"12345\"}");
        //numero de securité invalide
        a.payment("{\"cardNumber\": \"4430133265367210\", \"expirationDate\": \"09-20\", \"cardSecurityCode\": \"23\", \"commandN\": \"12345\"}");
        //carte validée
        a.payment("{\"cardNumber\": \"4430133265367210\", \"expirationDate\": \"09-20\", \"cardSecurityCode\": \"123\", \"commandN\": \"12345\"}");
    }

}