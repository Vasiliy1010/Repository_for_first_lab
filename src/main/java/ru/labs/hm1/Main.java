package ru.labs.hm1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.labs.hm1.gui.MissionDesktopFrame;
import ru.labs.hm1.service.MissionService;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        System.out.println(java.awt.GraphicsEnvironment.isHeadless());
        SpringApplication app = new SpringApplication(Main.class);
        app.setHeadless(false);

        ConfigurableApplicationContext context = app.run(args);

        MissionService missionService = context.getBean(MissionService.class);

        SwingUtilities.invokeLater(() -> {
            MissionDesktopFrame frame = new MissionDesktopFrame(missionService);
            frame.setVisible(true);
        });
    }
}