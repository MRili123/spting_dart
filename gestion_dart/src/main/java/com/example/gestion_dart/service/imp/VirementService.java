package com.example.gestion_dart.service.imp;
import java.util.List;


import com.example.gestion_dart.entity.*;
import com.example.gestion_dart.repository.DartRepository;
import com.example.gestion_dart.repository.ParticipationRepository;
import com.example.gestion_dart.repository.VirementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.gestion_dart.repository.NotificationRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VirementService {

    @Autowired
    private DartRepository dartRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private VirementRepository virementRepository;

    @Autowired
    private NotificationRepository notificationRepository;
    @Transactional
    public String userPay(Long dartId, Long userId) {
        Participation participation = participationRepository.findByDartIdAndUserId(dartId, userId);

        if (participation != null) {

            participation.setA_paye(true);
            participationRepository.save(participation);
        } else {
            throw new IllegalArgumentException("No participation found for dart_id: " + dartId + " and user_id: " + userId);
        }
        return null;
    }
    public String processVirement(Long dartId) {
        Dart dart = dartRepository.findById(dartId).orElse(null);
        if (dart == null) {
            return "redirect:/les_par";
        }


        Integer currentTurn = dart.getCurrent_turn();



        long participationCount = participationRepository.count();


        if (currentTurn <= participationCount) {

            Participation participation = participationRepository.findBySort(currentTurn);
            if (participation == null || !participation.getA_paye()) {
                return "redirect:/les_par";
            }

            User user = participation.getUser();


            Virement virement = new Virement(user, dart);
            virementRepository.save(virement);


            String notificationMessage = "Vous avez reçu un virement";
            Notification notification = new Notification(user, "Type", notificationMessage);
            notificationRepository.save(notification);


            participation.setIs_paid(true);
            participationRepository.save(participation);

            List<Participation> allParticipations = participationRepository.findAll();
            for (Participation p : allParticipations) {
                p.setA_paye(false);
            }
            participationRepository.saveAll(allParticipations);

            currentTurn++;
            dart.setCurrent_turn(currentTurn);
            dartRepository.save(dart);

            return "redirect:/admin";
        } else {

            return "redirect:/les_par";
        }
    }
}

