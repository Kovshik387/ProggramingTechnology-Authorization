package com.auth.ProgrammingTechnology.Authorization.services;

import com.auth.ProgrammingTechnology.Authorization.dal.model.Account;
import com.auth.ProgrammingTechnology.Authorization.dal.model.AuthAccount;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.net.URL;


@Component
@AllArgsConstructor
public class MessageServiceImpl {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private final TemplateEngine templateEngine;
    @Autowired
    private final Environment environment;
    public boolean sendMessage(AuthAccount account,String code){
        try{
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mimeMessage,"UTF-8"){{
                setTo(account.getEmail());
                setSubject("Восстановление пароля");
            }};

            var url = environment.getProperty("site") + code + ".." + account.getId();

            System.out.println(url);
            var context = createContext(url);
            helper.setText(templateEngine.process("password-template",context),true);
            helper.setFrom("kursprojecttask5fantokin@gmail.com");

            mailSender.send(mimeMessage);
            return true;
        }
        catch (Exception exception){
            System.out.println("Не удалось отправить письмо\n" + exception.getMessage());
        }
        return false;
    }

    private Context createContext(String url){
        var context = new Context();
        context.setVariable("url",url);
        return context;
    }

}
