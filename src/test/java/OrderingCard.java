import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class OrderingCard {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    String date(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void orderCard() {
        $("[data-test-id=city] input").setValue("Улан-Удэ");
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(date(3));
        $("[data-test-id=name] input").setValue("Лупа Пупина");
        $("[data-test-id=phone] input").setValue("+79999999999");
        $("[data-test-id=agreement]").click();
        $$("button").findBy(Condition.exactText("Забронировать")).shouldBe(visible, Duration.ofSeconds(15)).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + date(3)), Duration.ofSeconds(15))
                .shouldBe(visible);
    }
}
