package com.meghrajswami.bitex.domain;

/**
 * Created by megh on 5/25/2017.
 */
public class View {
    public interface PublicSummary {
    }

    public interface PublicDetail extends PublicSummary {
    }

    public interface RoleUser extends PublicDetail {
    }

    public interface RoleOwner extends RoleUser {
    }

    public interface RoleAdmin extends RoleOwner {
    }
}