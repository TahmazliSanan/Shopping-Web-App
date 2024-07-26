package org.pronet.shoppie.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.pronet.shoppie.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Component
public class AccountUtils {
    @Autowired
    private JavaMailSender mailSender;
    String contentMessage = null;

    public Boolean sendMail(String url, String recipientEmail)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("tehmezlisenan11@gmail.com", "Shoppie");
        helper.setTo(recipientEmail);
        String body = "<p>Hello,</p>" + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>" + "<p><a href=\"" + url
                + "\">Change my password</a></p>";
        helper.setSubject("Reset Password");
        helper.setText(body, true);
        mailSender.send(message);
        return true;
    }

    public void sendMailForOrderProcess(Order order, String status)
            throws MessagingException, UnsupportedEncodingException {
        contentMessage = "<p>Hello [[name]],</p>"
                + "<p>Thank you order <b>[[orderStatus]]</b>.</p>"
                + "<p><b>Product Details:</b></p>"
                + "<p>Name : [[productName]]</p>"
                + "<p>Category : [[category]]</p>"
                + "<p>Quantity : [[quantity]]</p>"
                + "<p>Price : [[price]] $</p>"
                + "<p>Payment Type : [[paymentType]]</p>";
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom("tehmezlisenan11@gmail.com", "Shoppie");
        helper.setTo(order.getOrderAddress().getEmail());
        contentMessage = contentMessage.replace("[[name]]",order.getOrderAddress().getFirstName());
        contentMessage = contentMessage.replace("[[orderStatus]]",status);
        contentMessage = contentMessage.replace("[[productName]]", order.getProduct().getName());
        contentMessage = contentMessage.replace("[[category]]", order.getProduct().getCategory());
        contentMessage = contentMessage.replace("[[quantity]]", order.getQuantity().toString());
        contentMessage = contentMessage.replace("[[price]]", order.getPrice().toString());
        contentMessage = contentMessage.replace("[[paymentType]]", order.getPaymentType());
        helper.setSubject("Order Status");
        helper.setText(contentMessage, true);
        mailSender.send(message);
    }

    public static String generateUrl(HttpServletRequest request) {
        String siteUrl = request.getRequestURL().toString();
        return siteUrl.replace(request.getServletPath(), "");
    }
}
