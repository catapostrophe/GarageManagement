package billing;

import static org.junit.jupiter.api.Assertions.assertEquals;

class billTest {

    @org.junit.jupiter.api.Test
    void calculateBill() {

        double hr = 300;
        double t = 44;
        double sq = 1;
        double sp = 500;
        double d = 2;
        double expected = 752.60;
        double result = Bill.calculateBill(hr, t, sq, sp, d);

        assertEquals(expected, result);

    }
}