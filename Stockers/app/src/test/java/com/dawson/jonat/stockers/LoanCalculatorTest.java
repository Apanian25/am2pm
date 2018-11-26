package com.dawson.jonat.stockers;

import com.dawson.jonat.stockers.LoanCalculator.LoanCalculator;
import com.dawson.jonat.stockers.LoanCalculator.LoanPayoutSummary;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(Parameterized.class)
public class LoanCalculatorTest {

    /**
     * What this will test:
     * 1-To ensure no interest will be charged if the user pays off all his debt at the end of the month
     */
    private static Object[] test1 = {LoanCalculator.calculateInterestPayoutSummary(10.00, 20, 10, 1),
            new LoanPayoutSummary(1, 10.00, 0, 10, 0)};

    /**
     * What this will test:
     * 1-Ensure expected results are obtained when the debt isn't payed off during the time
     */
    private static Object[] test2 = {LoanCalculator.calculateInterestPayoutSummary(1000.00, 20, 10, 1),
            new LoanPayoutSummary(12, 1000.00, 0, 120, 0)};

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                /*{double debt, double rate, double minPayment, int years, new LoanPayoutSummary(int monthsToPayOff,
                double originalBalance, double interestAccumulated, double totalPaid, double amountLeftToPay)}*/
                test1, test2
        });
    }

    //Parameters that are use to perform the action
    private LoanPayoutSummary results;

    //Parameters that are used to validate
    private LoanPayoutSummary expectedResults;

    /**
     * Initialize the result and the expected results
     *
     * @param results
     * @param expectedResults
     */
    public LoanCalculatorTest(LoanPayoutSummary results, LoanPayoutSummary expectedResults) {
        this.results = results;
        this.expectedResults = expectedResults;
    }

    /**
     * This method will simply test to ensure that the months that are calculated for it to take to
     * pay off the credit debt is accurate.
     */
    @Test
    public void testTimeToPayOff() {
        assertEquals(expectedResults.getMonthsToPayOff(), results.getMonthsToPayOff());
    }

    /**
     * Test to ensure that the original balance is what it's expected to be
     */
    @Test
    public void testOriginalAmountOwed() {
        //Just ensure that the first two digits are the same
        assertEquals(expectedResults.getOriginalAmountOwed(), results.getOriginalAmountOwed(), 0.001);
    }

    /**
     * Test to ensure that the amount of interest accumulated that has been calculated is the
     * accurate amount of interest that should be accumulated
     */
    @Test
    public void testInterestAccumulated() {
        //Just ensure that the first two digits are the same
        assertEquals(expectedResults.getInterestAccumulated(), results.getInterestAccumulated(), 0.001);
    }


    /**
     * Test to ensure that the total amount paid that was calculated is the expected result
     */
    @Test
    public void testTotalPaid() {
        //Just ensure that the first two digits are the same
        assertEquals(expectedResults.getTotalPaid(), results.getTotalPaid(), 0.001);
    }

    /**
     * Test to ensure that the amount left to pay that was caculated is the actual amount that is
     * left to pay.
     */
    @Test
    public void testAmountLeftToPay() {
        //Just ensure that the first two digits are the same
        assertEquals(expectedResults.getAmountLeftToPay(), results.getAmountLeftToPay(), 0.001);
    }
}