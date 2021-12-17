package ch.rabanti.nanoxlsx4j.misc;

import ch.rabanti.nanoxlsx4j.Helper;
import ch.rabanti.nanoxlsx4j.exceptions.FormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

public class HelperTest {

    @DisplayName("Test of the getOADateString function")
    @ParameterizedTest(name = "Given date {0} should lead to the OADate number {1}")
    @CsvSource({
            "01.01.1900 00:00:00, 1",
            "02.01.1900 12:35:20, 2.5245370370370401",
            "27.02.1900 00:00:00, 58",
            "28.02.1900 00:00:00, 59",
            "28.02.1900 12:30:32, 59.521203703703705",
            "01.03.1900 00:00:00, 61",
            "01.03.1900 08:08:11, 61.339016203703707",
            "20.05.1960 22:11:05, 22056.924363425926",
            "01.01.2021 00:00:00, 44197",
            "12.12.5870 11:30:12, 1450360.47930556",
    })
    void getOADateTimeStringTest(String dateString, String expectedOaDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        Date date = formatter.parse(dateString);
        String oaDate = Helper.getOADateString(date);
        float expected = Float.parseFloat(expectedOaDate);
        float given = Float.parseFloat(oaDate);
        float threshold = 0.000000001f; // Ignore everything below a millisecond
        assertTrue(Math.abs(expected - given) < threshold);
    }

    @DisplayName("Test of the getOADate function")
    @ParameterizedTest(name = "Given date {0} should lead to the OADate number {1}")
    @CsvSource({
            "01.01.1900 00:00:00, 1",
            "02.01.1900 12:35:20, 2.5245370370370401",
            "27.02.1900 00:00:00, 58",
            "28.02.1900 00:00:00, 59",
            "28.02.1900 12:30:32, 59.521203703703705",
            "01.03.1900 00:00:00, 61",
            "01.03.1900 08:08:11, 61.339016203703707",
            "20.05.1960 22:11:05, 22056.924363425926",
            "01.01.2021 00:00:00, 44197",
            "12.12.5870 11:30:12, 1450360.47930556",
    })
    void getOADateTimeTest(String dateString, double expectedOaDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        Date date = formatter.parse(dateString);
        double oaDate = Helper.getOADate(date);
        float threshold = 0.00000001f; // Ignore everything below a millisecond (double precision may vary)
        assertTrue(Math.abs(expectedOaDate - oaDate) < threshold);
    }

    @DisplayName("Test of the failing getOADateString function on invalid dates")
    @ParameterizedTest(name = "Given date {0} should lead to an exception")
    @CsvSource({
            "01.01.0001 00:00:00",
            "18.05.0712 11:15:02",
            "31.12.1899 23:59:59",
            "01.01.10000 00:00:00",
    })
    void getOADateStringFailTest(String dateString) throws ParseException {
        // Note: Dates beyond the year 10000 may not be tested wit other frameworks
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        Date date = formatter.parse(dateString);
        assertThrows(FormatException.class, () -> Helper.getOADateString(date));
    }

    @DisplayName("Test of the failing getOADate function on invalid dates")
    @ParameterizedTest(name = "Given date {0} should lead to an exception")
    @CsvSource({
            "01.01.0001 00:00:00",
            "18.05.0712 11:15:02",
            "31.12.1899 23:59:59",
            "01.01.10000 00:00:00",
    })
    void getOADateFailTest(String dateString) throws ParseException {
        // Note: Dates beyond the year 10000 may not be tested wit other frameworks
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        Date date = formatter.parse(dateString);
        assertThrows(FormatException.class, () -> Helper.getOADate(date));
    }

    @DisplayName("Test of the failing getOADateString function on a null Date")
    @Test()
    void getOADateStringFailTest2() {
        assertThrows(FormatException.class, () -> Helper.getOADateString(null));
    }

    @DisplayName("Test of the failing getOADate function on a null Date")
    @Test()
    void getOADateFailTest() {
        assertThrows(FormatException.class, () -> Helper.getOADate(null));
    }

    @DisplayName("Test of the getOATimeString function")
    @ParameterizedTest(name = "Given value {0} should lead to the OaDate {1}")
    @CsvSource({
            "00:00:00, 0.0",
            "12:00:00, 0.5",
            "23:59:59,  0.999988425925926",
            "13:11:10, 0.549421296296296",
            "18:00:00, 0.75",
    })
    void getOATimeStringTest(String timeString, String expectedOaTime) throws ParseException {
        Duration time = Helper.parseTime(timeString, "HH:mm:ss");
        String oaDate = Helper.getOATimeString(time);
        float expected = Float.parseFloat(expectedOaTime);
        float given = Float.parseFloat(oaDate);
        float threshold = 0.000000001f; // Ignore everything below a millisecond
        assertTrue(Math.abs(expected - given) < threshold);
    }

    @DisplayName("Test of the getOATime function")
    @ParameterizedTest(name = "Given value {0} should lead to the OaDate {1}")
    @CsvSource({
            "00:00:00, 0.0",
            "12:00:00, 0.5",
            "23:59:59,  0.999988425925926",
            "13:11:10, 0.549421296296296",
            "18:00:00, 0.75",
    })
    void getOATimeTest(String timeString, double expectedOaTime) throws ParseException {
        Duration time = Helper.parseTime(timeString, "HH:mm:ss");
        double oaTime = Helper.getOATime(time);
        float threshold = 0.000000001f; // Ignore everything below a millisecond
        assertTrue(Math.abs(expectedOaTime - oaTime) < threshold);
    }

    @DisplayName("Test of the failing getOATimeString function on a null LocalTime")
    @Test()
    void getOATimeStringFailTest2() {
        assertThrows(FormatException.class, () -> Helper.getOATimeString(null));
    }

    @DisplayName("Test of the failing getOATime function on a null LocalTime")
    @Test()
    void getOATimeFailTest() {
        assertThrows(FormatException.class, () -> Helper.getOATime(null));
    }


    @DisplayName("Test of the getInternalColumnWidth function")
    @ParameterizedTest(name = "Given value {0} should lead to the internal width {1}")
    @CsvSource({
            "0.5, 0.85546875",
            "1, 1.7109375",
            "10, 10.7109375",
            "15, 15.7109375",
            "60, 60.7109375",
            "254, 254.7109375",
            "255, 255.7109375",
            "0, 0f",
    })
    void getInternalColumnWidthTest(float width, float expectedInternalWidth) {
        float internalWidth = Helper.getInternalColumnWidth(width);
        assertEquals(expectedInternalWidth, internalWidth);
    }

    @DisplayName("Test of the failing GetInternalColumnWidth function on invalid column widths")
    @ParameterizedTest(name = "Given value {0} should lead to an exception")
    @CsvSource({
            "-0.1",
            "-10",
            "255.01",
            "10000",
    })
    void getInternalColumnWidthFailTest(float width) {
        assertThrows(FormatException.class, () -> Helper.getInternalColumnWidth(width));
    }

    @DisplayName("Test of the getInternalRowHeight function")
    @ParameterizedTest(name = "Given value {0} should lead to the internal row height {1}")
    @CsvSource({
            "0.1, 0f",
            "0.5, 0.75",
            "1, 0.75",
            "10, 9.75",
            "15, 15",
            "409, 408.75",
            "409.5, 409.5",
            "0, 0f",
    })
    void getInternalRowHeightTest(float height, float expectedInternalHeight) {
        float internalHeight = Helper.getInternalRowHeight(height);
        assertEquals(expectedInternalHeight, internalHeight);
    }

    @DisplayName("Test of the failing getInternalRowHeight function on invalid row heights")
    @ParameterizedTest(name = "Given value {0} should lead to n exception")
    @CsvSource({
            "-0.1",
            "-10",
            "409.6",
            "10000",
    })
    void getInternalRowHeightFailTest(float height) {
        assertThrows(FormatException.class, () -> Helper.getInternalRowHeight(height));
    }

    @DisplayName("Test of the getInternalPaneSplitWidth function")
    @ParameterizedTest(name = "Given value {0} should lead to width {1}")
    @CsvSource({
            "0.1f, 390f",
            "1f, 390f",
            "18.5, 2415f",
            "32f, 3825f",
            "255, 27240f",
            "256, 27345f",
            "1000, 105465f",
            "0, 390f",
            "-1, 390f",
            "-10, 390f",
    })
    void getInternalPaneSplitWidthTest(float width, float expectedSplitWidth) {
        float splitWidth = Helper.getInternalPaneSplitWidth(width);
        assertEquals(expectedSplitWidth, splitWidth);
    }

    @DisplayName("Test of the getInternalPaneSplitHeight function")
    @ParameterizedTest(name = "Given value {0} should lead to the height {1}")
    @CsvSource({
            "0.1f, 302f",
            "0.5f, 310f",
            "1f, 320f",
            "15f, 600f",
            "409.5, 8490f",
            "500, 10300f",
            "0, 300f",
            "-1, 300f",
            "-10, 300f",
    })
    void getInternalPaneSplitHeightTest(float height, float expectedSplitHeight) {
        float splitHeight = Helper.getInternalPaneSplitHeight(height);
        assertEquals(expectedSplitHeight, splitHeight);
    }

    @DisplayName("Test of the getDateFromOA function")
    @ParameterizedTest(name = "Given value {0} should lead to the date {1}")
    @CsvSource({
            "1, '01.01.1900 00:00:00'",
            "2.5245370370370401, '02.01.1900 12:35:20'",
            "58, '27.02.1900 00:00:00'",
            "59, '28.02.1900 00:00:00'",
            "59.521203703703705, '28.02.1900 12:30:32'",
            "61, '01.03.1900 00:00:00'",
            "61.339016203703707, '01.03.1900 08:08:11'",
            "22056.924363425926, '20.05.1960 22:11:05'",
            "44197, '01.01.2021 00:00:00'",
            "1450360.47930556, '12.12.5870 11:30:12'",
    })
    void getDateFromOATest(double givenValue, String expectedDateString) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.US);
        Date expectedDate = formatter.parse(expectedDateString);
        Date date = Helper.getDateFromOA(givenValue);
        assertEquals(expectedDate, date);
    }


}
