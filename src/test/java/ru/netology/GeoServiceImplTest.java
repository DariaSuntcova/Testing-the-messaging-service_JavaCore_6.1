package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;

import java.util.stream.Stream;

public class GeoServiceImplTest {
    GeoService geoService = new GeoServiceImpl();


    @ParameterizedTest
    @MethodSource("argumentsStream")
    public void shouldByIdTest(String ip, Location expected) {

        Assertions.assertEquals(expected, geoService.byIp(ip));
    }

    public static Stream<Arguments> argumentsStream() {
        return Stream.of(
                Arguments.of("127.0.0.1", new Location(null, null, null, 0), "Welcome"),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.12.000.1", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.45.222.177", new Location("New York", Country.USA, null, 0)),
                Arguments.of("125.464.464.54", null),
                Arguments.of("drhshshsrh", null)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "125.44,25.55",
            "0,1.54",
            "546.64, 0",
            "0,0",
            "-1,11.54"
    })
    public void shouldByCoordinatesTest(double latitude, double longitude) {
        Assertions.assertThrows(RuntimeException.class, () -> geoService.byCoordinates(latitude, longitude));
    }
}
