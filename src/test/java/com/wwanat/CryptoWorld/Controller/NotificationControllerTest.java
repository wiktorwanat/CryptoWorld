package com.wwanat.CryptoWorld.Controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwanat.CryptoWorld.Model.Cryptocurrency;
import com.wwanat.CryptoWorld.Model.Notification;
import com.wwanat.CryptoWorld.Model.Types.NotificationType;
import com.wwanat.CryptoWorld.Model.Types.UserRole;
import com.wwanat.CryptoWorld.Model.User;
import com.wwanat.CryptoWorld.Security.JWT.AuthEntryPointJwt;
import com.wwanat.CryptoWorld.Security.JWT.JwtUtils;
import com.wwanat.CryptoWorld.Service.CryptocurrencyDetailsService;
import com.wwanat.CryptoWorld.Service.NotificationService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.wwanat.CryptoWorld.Controller.CryptocurrencyControllerTest.asJsonString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(value = NotificationController.class)
class NotificationControllerTest {

    static String apiUrl = "http://localhost:8080/api/";

    private Cryptocurrency cryptocurrencyTestObject;
    private Notification notificationObject;
    private List<Notification> notificationsListObject;
    private User testUserObject;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private AuthEntryPointJwt authEntryPointJwt;

    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private NotificationService notificationService;

    @MockBean
    private CryptocurrencyDetailsService cryptocurrencyDetailsService;

    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp() throws Exception {
        this.cryptocurrencyTestObject = new Cryptocurrency("Bitcoin", "BTC", "bitcoin", 33826.0, 600000000.0, 100.0, 100.0, 100.0, 100000000.0, null);
        this.testUserObject = new User("user", "user", "user@gmail.com");
        List roleList = new ArrayList();
        roleList.add(UserRole.ROLE_USER);
        this.testUserObject.setRoles(roleList);
        this.testUserObject.addFavouriteCryptocurrency(this.cryptocurrencyTestObject);
        this.notificationObject = new Notification(1000.0, NotificationType.OVER, this.cryptocurrencyTestObject, this.testUserObject);
        this.notificationsListObject = new ArrayList<>();
        this.notificationsListObject.add(this.notificationObject);
    }

    public NotificationControllerTest() {
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_USER")
    @Test
    public void getUserNotificationsTest_WithUSERROLE_shouldReturn200withNotificationList() throws Exception {
        given(notificationService.getSpecificUserNotifications("1050106266")).willReturn(this.notificationsListObject);
        String response = mvc.perform(get(apiUrl + "notifications/myNotifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Notification> responseList = mapper.readValue(response, new TypeReference<List<Notification>>() {
        });
        Assert.assertTrue(responseList.size() == 1);
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_ADMIN")
    @Test
    public void getUserNotificationsTest_WithADMINROLE_shouldReturn200withNotificationList() throws Exception {
        given(notificationService.getSpecificUserNotifications("1050106266")).willReturn(this.notificationsListObject);
        String response = mvc.perform(get(apiUrl + "notifications/myNotifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        List<Notification> responseList = mapper.readValue(response, new TypeReference<List<Notification>>() {
        });
        Assert.assertTrue(responseList.size() == 1);
    }


    @Test
    public void getUserNotificationsTest_WithoutAuthentication_shouldReturn403() throws Exception {
        List<Notification> notificationEmptyList = new ArrayList<>();
        given(notificationService.getAllNotifications()).willReturn(notificationEmptyList);

        String response = mvc.perform(get(apiUrl + "notifications/myNotifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(response.isEmpty());
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_USER")
    @Test
    public void updateNotificationsTest_WithUSERROLE_shouldReturn200() throws Exception {
        given(notificationService.update(this.notificationObject)).willReturn(this.notificationObject);
        mvc.perform(put(apiUrl + "notifications/update")
                        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(this.notificationObject)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_ADMIN")
    @Test
    public void updateNotificationsTest_WithADMINROLE_shouldReturn200() throws Exception {
        given(notificationService.update(this.notificationObject)).willReturn(this.notificationObject);
        mvc.perform(put(apiUrl + "notifications/update")
                        .contentType(MediaType.APPLICATION_JSON).content(asJsonString(this.notificationObject)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_ADMIN")
    @Test
    public void updateNotificationsTest_WithoutContent_shouldReturn400() throws Exception {
        given(notificationService.update(this.notificationObject)).willReturn(this.notificationObject);
        mvc.perform(put(apiUrl + "notifications/update")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().is(400));
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_ADMIN")
    @Test
    public void createNotificationsTest_WithoutContent_shouldReturn400() throws Exception {
        given(notificationService.update(this.notificationObject)).willReturn(this.notificationObject);
        mvc.perform(post(apiUrl + "notifications")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400));
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_ADMIN")
    @Test
    public void removeNotificationTest_ROLE_UADMIN_shouldReturn200() throws Exception {
        mvc.perform(delete(apiUrl + "/notifications/myNotifications/{notificationId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_USER")
    @Test
    public void removeNotificationTest_ROLE_USER_shouldReturn200() throws Exception {
        mvc.perform(delete(apiUrl + "/notifications/myNotifications/{notificationId}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200));
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_ADMIN")
    @Test
    public void getAllNotificationsTest_WithROLEADMIN_shouldReturn200andNotEmptyNotificationList() throws Exception {
        given(notificationService.getAllNotifications()).willReturn(this.notificationsListObject);
        String response = mvc.perform(get(apiUrl + "notifications/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(!response.isEmpty());
    }

    @WithMockUser(username = "1050106266",
            password = "123456",
            authorities = "ROLE_USER")
    @Test
    public void getAllNotificationsTest_WithROLEUSER_shouldReturn403() throws Exception {
        mvc.perform(get(apiUrl + "notifications/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(403));
    }

    @Test
    public void getAllNotificationsTest_WithoutAuthentication_shouldReturn403() throws Exception {
        List<Notification> notificationEmptyList = new ArrayList<>();
        given(notificationService.getAllNotifications()).willReturn(notificationEmptyList);

        String response = mvc.perform(get(apiUrl + "notifications/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Assert.assertTrue(response.isEmpty());
    }


}
