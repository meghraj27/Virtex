package com.meghrajswami.virtex.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Meghraj on 6/30/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "`USER`",
        indexes = {
                @Index(columnList = "NAME")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class User extends AuditableEntity implements Serializable {

    @JsonView(View.PublicSummary.class)
    private String photoUrlSmall;

    @JsonView(View.PublicSummary.class)
    private String photoUrlMedium;

    @JsonView(View.PublicSummary.class)
    private String photoUrlLarge;

    @JsonView(View.PublicSummary.class)
    @Column(name = "NAME", length = 100)
    private String name;

    @JsonView(View.RoleOwner.class)
    @Column(unique = true)
    @NotNull
    private String username;

    @JsonView(View.RoleAdmin.class)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ToString.Exclude
    private String password;

    @JsonView(View.RoleAdmin.class)
    @NotNull
    private Boolean enabled;

    @JsonView(View.RoleAdmin.class)
    @ManyToMany
    @Setter(value = AccessLevel.NONE)
    private Collection<Role> roles = new ArrayList<>();

    @JsonView(View.RoleOwner.class)
    @NotNull
    private String email;

    @JsonView(View.RoleOwner.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @NotNull
    private Boolean emailVerified;

    @JsonView(View.RoleUser.class)
    private String description;

    @JsonView(View.RoleAdmin.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long fbId;

    @JsonView(View.RoleAdmin.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(length = 30)
    private String googleId;

    @JsonView(View.RoleAdmin.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String authSite; //value = facebook or google

    @JsonView(View.RoleAdmin.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String accessToken;

    @JsonView(View.RoleAdmin.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String refreshToken;

    @JsonView(View.RoleAdmin.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long tokenExpireAt;

    @JsonView(View.RoleUser.class)
    private String facebookLink;

    @JsonView(View.RoleUser.class)
    private String linkedInLink;

    @JsonView(View.RoleUser.class)
    private String twitterLink;

    @JsonView(View.RoleUser.class)
    private String googleLink;

    @JsonView(View.RoleOwner.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long paidOrderId;

    @JsonView(View.RoleOwner.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private String defaultCurrency;

    @JsonView(View.RoleOwner.class)
    @Column(precision = 19, scale = 4)
    private BigDecimal mainBalance;

    @JsonView(View.RoleOwner.class)
    @Column(precision = 19, scale = 4)
    private BigDecimal availableMargin;

    @ElementCollection(targetClass = Holding.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_HOLDING")
    // @MapKey(name = "symbol")
    @MapKeyJoinColumn(name = "HOLDING_ID", referencedColumnName = "ID")
    @MapKeyEnumerated(EnumType.STRING)
    private Map<Symbol, Holding> holdings = new HashMap<>();

    public User(String name,
                String email,
                String password) {
        this.name = name;
        this.username = email;
        this.email = email;
        this.password = password;
        this.enabled = true;
        this.emailVerified = false;
    }

    public User(String name,
                String email,
                String photoUrlLarge,
                boolean emailVerified) {
        this.name = name;
        this.username = email;
        this.email = email;
        this.photoUrlLarge = photoUrlLarge;
        this.enabled = true;
        this.emailVerified = emailVerified;
    }

    public void addRole(Role role) {
        roles.add(role);
        //        role.getLinkedUsers().add(this);
    }

    public void removeRole(Role role) {
        roles.remove(role);
        //        role.getLinkedUsers().remove(this);
    }

    public Currency getDefaultCurrency() {
        return Currency.getInstance(defaultCurrency);
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency.getCurrencyCode();
    }

    public void addDebugData(String data) {
        JsonObject jsonObjectDebugData = new Gson().fromJson(data, JsonObject.class);
        JsonObject o = jsonObjectDebugData.getAsJsonObject("data");
        setFbId(o.get("user_id").getAsLong());
        setTokenExpireAt(o.get("expires_at").getAsLong());
    }

    public Holding getHolding(Symbol symbol) {
        return holdings.get(symbol);
    }

    public void blockMargin(BigDecimal margin) {
        availableMargin = availableMargin.subtract(margin);
    }

    public void releaseMargin(BigDecimal margin) {
        availableMargin = availableMargin.add(margin);
    }

    // TODO: 12/5/2017 dependent on Side enum from parity match lib, need to be fixed
    public void adjustMarginOnTradeBuy(Symbol symbol,
                                       BigDecimal executionPrice,
                                       BigDecimal orderPrice,
                                       BigDecimal quantity) {
        mainBalance = mainBalance.subtract(executionPrice.multiply(quantity));
        // am+=q*(op-ep)
        ///av=available margin, q=executed quantity, op= order price, ep= execution price
        availableMargin = availableMargin.add(quantity.multiply(orderPrice.subtract(executionPrice)));

        // TODO: 12/5/2017 holding could be null
        if ((holdings.get(symbol)) == null)
            holdings.put(symbol, new Holding(symbol, quantity));
        else holdings.get(symbol).add(quantity);
    }

    public void adjustMarginOnTradeSell(Symbol symbol,
                                        BigDecimal executionPrice,
                                        BigDecimal quantity) {
        mainBalance = mainBalance.add(executionPrice.multiply(quantity));
        availableMargin = availableMargin.add(executionPrice.multiply(quantity));

        holdings.get(symbol).remove(quantity);
    }

    public static class AuthSite {
        public static final String FACEBOOK = "facebook";
        public static final String GOOGLE = "google";
    }

}
