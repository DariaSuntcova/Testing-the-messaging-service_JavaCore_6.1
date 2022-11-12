package ru.netology;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

public class LocalizationServiceImplTest {

    LocalizationServiceImpl localizationService = new LocalizationServiceImpl();

    @ParameterizedTest
    @CsvSource({
            "RUSSIA, Добро пожаловать",
            "GERMANY,Welcome",
            "USA,Welcome",
            "BRAZIL, Welcome"
    })
    public void shouldLocaleTest(Country actual, String expected) {

        Assertions.assertEquals(localizationService.locale(actual), expected);

    }
}
