package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;


public class MessageSenderImplTest {

    public static final String IP_ADDRESS_HEADER = "x-real-ip";
    GeoService geoService = Mockito.mock(GeoService.class);
    LocalizationServiceImpl localizationService = Mockito.mock(LocalizationServiceImpl.class);
    MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);


    @ParameterizedTest
    @MethodSource("argumentsStream")
    public void shouldSendTest(String ip, Location location, String message) {

        Mockito.when(geoService.byIp(ip))
                .thenReturn(location);

        Mockito.when(localizationService.locale(geoService.byIp(ip).getCountry()))
                .thenReturn(message);


        Map<String, String> headers = new HashMap<>();
        headers.put(IP_ADDRESS_HEADER, ip);

        Assertions.assertEquals(message, messageSender.send(headers));
        System.out.println();

    }

    public static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0), "Welcome"),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15), "Добро пожаловать"),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32), "Welcome"),
                Arguments.of("172.12.000.1", new Location("Moscow", Country.RUSSIA, null, 0), "Добро пожаловать"),
                Arguments.of("96.45.222.177", new Location("New York", Country.USA, null, 0), "Welcome")
        );
    }


}
