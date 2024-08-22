package com.coddingshuttle.SecurityApp.SecuityApplication.services;

import com.coddingshuttle.SecurityApp.SecuityApplication.entities.Session;
import com.coddingshuttle.SecurityApp.SecuityApplication.entities.User;
import com.coddingshuttle.SecurityApp.SecuityApplication.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {
    private final SessionRepository sessionRepository;
    private final int SESSION_LIMIT=2;

    public void generateNewSession(User user,String refreshToken){
        //1.Return List of the sessions for the user
        List<Session> Usersessions=sessionRepository.findByUser(user);

        //2.Check if the SESSION_LIMIT is exceeded
        if (Usersessions.size()==SESSION_LIMIT){ //size() returns no of element in the list
            //Reached the limit, Remove the last Recently(oldest) used session

            //2.1 sort the Sessions using comparator and sort method based on lastUsedAt time
            Usersessions.sort(Comparator.comparing(Session::getLastUsedAt));  //ascending

            //2.2 get the leastRecentlyUsed/Oldest session from UserSession list
            Session leastRecentlyUsed=Usersessions.getFirst();

            //2.3 delete the leastRecentlyUsed session
            sessionRepository.delete(leastRecentlyUsed);
        }

        //3.if the SESSION_LIMIT is not exceeded,Create New session for the user and Save it to Session entity

        Session session=Session.builder()
                        .refreshToken(refreshToken)
                                .user(user).build();

        sessionRepository.save(session);
    }

    public void validateSession(String refreshToken){
        //1.Check if the Refresh Token is present in the Session or not(i.e Active session),
        //If present return the session
        Session session = sessionRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Session not found for refreshToken: "+refreshToken));
        //2.Update LastUsedAt time
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);

    }




}
