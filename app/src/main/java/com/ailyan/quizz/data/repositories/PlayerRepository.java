package com.ailyan.quizz.data.repositories;

import com.ailyan.quizz.data.sources.remote.RetrofitInstance;
import com.ailyan.quizz.data.sources.remote.beans.AuthResponse;
import com.ailyan.quizz.data.sources.remote.beans.CheckSessionResponse;
import com.ailyan.quizz.data.sources.remote.services.PlayerService;

import io.reactivex.rxjava3.core.Single;

public class PlayerRepository {
    private final PlayerService playerService;

    public PlayerRepository() {
        playerService = RetrofitInstance.getInstance().create(PlayerService.class);
    }

    public Single<AuthResponse> login(String username, String password) {
        return playerService.login(username, password);
    }

    public Single<CheckSessionResponse> checkSession(String username, String session) {
        return playerService.checkSession(username, session);
    }
}
