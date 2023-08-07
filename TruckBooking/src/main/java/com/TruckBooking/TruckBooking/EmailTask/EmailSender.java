package com.TruckBooking.TruckBooking.EmailTask;

import com.TruckBooking.TruckBooking.Dao.TransporterEmailDao;
import com.TruckBooking.TruckBooking.Entities.Load;
import com.TruckBooking.TruckBooking.Entities.TransporterEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import java.nio.charset.StandardCharsets;
import java.util.List;
@Component
public class EmailSender {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TransporterEmailDao transporterEmailDao;
    @Autowired
    private EntityManager entityManager;

    @Value("${SHIPPER_HOST}")
    private String shipperHost;

    @Scheduled(fixedRate = 300000)
    public void sendEmail() throws MessagingException {
        List<TransporterEmail> emailList=transporterEmailDao.findByStatus("not-sent");
        for(TransporterEmail transporterEmail:emailList){

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, StandardCharsets.UTF_8.name());
            Load load=transporterEmail.getLoad();

            String shipperId=load.getPostLoadId();
            int index=shipperId.indexOf(":");
            shipperId=shipperId.replace(shipperId.substring(0,index),"shipper");
            RestTemplate restTemplate = new RestTemplate();
            String apiUrl = shipperHost + "/shipper/"+shipperId; // Append the API endpoint to the shipperHost
            ResponseEntity<Shipper> response = restTemplate.getForEntity(apiUrl, Shipper.class);
            Shipper shipper=response.getBody();
            String subject="RFQ for "+load.getWeight()+"MT "+"from " +load.getLoadingPointCity()+" to "+load.getUnloadingPointCity();
            String body="<h2>Load Details</h2>"+
                    "This company has posted a load: "+shipper.getShipperName()+"<br>"
                    +load.getLoadingPoint()+","+load.getLoadingPointCity()+","+load.getLoadingPointState()
                    +"<br> to <br>"+load.getUnloadingPoint()+","+load.getUnloadingPointCity()+","+load.getUnloadingPointState()+
                    "<hr>"+
                    "<h3>Requirements</h3>"+
                    "Truck Type: "+load.getTruckType()+"<br>"+
                    "Tyre :"+load.getNoOfTyres()+"<br>"+
                    "Weight :"+load.getWeight()+"<br>"+
                    "Product Type :"+load.getProductType();
            helper.setTo(transporterEmail.getEmail());
            helper.setSubject(subject);
            helper.setText(body,true);
            javaMailSender.send(message);
            transporterEmail.setStatus("sent");
            transporterEmailDao.save(transporterEmail);
        }
    }
}
