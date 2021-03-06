package com.dawson.jonat.stockers.Email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.text.Spanned;

import com.dawson.jonat.stockers.LoanCalculator.LoanPayoutSummary;
import com.dawson.jonat.stockers.R;

public class Email {

    public static void sendEmail(String to, String subject, LoanPayoutSummary results, Context context){
        final Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        //This line ensures that the intent is only handled by email apps
        emailIntent.setType("text/html");
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(formatText(results, context)));
        if (emailIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(Intent.createChooser(emailIntent, "Email:"));
        }
    }

    private static String formatText(LoanPayoutSummary results, Context context) {

        String text = context.getResources().getString(R.string.result) + "\n"
                + context.getResources().getString(R.string.ogBalance) + results.getOriginalAmountOwed() + "\n"
                + context.getResources().getString(R.string.timeToPay) + results.getMonthsToPayOff() + "\n"
                + context.getResources().getString(R.string.totalPaid) + results.getTotalPaid() + "\n"
                + context.getResources().getString(R.string.amountLeft) + results.getAmountLeftToPay();


        return text;
    }
//    /**
//     * Transforms a LoanPayoutSummary into a html table with all of the LoanPayoutSummary properties
//     * displayed as the table rows.
//     *
//     * @param results
//     * @return
//     */
//    private static String formatText(LoanPayoutSummary results) {
//        String text = "<html><body>";
//        text += "<table>";
//        text += genearateRow("Original Balance:", results.getOriginalAmountOwed() + "");
//        text += genearateRow("Time to pay off:", results.getMonthsToPayOff() + "");
//        text += genearateRow("Interest Accumulated:", results.getInterestAccumulated() + "");
//        text += genearateRow("Total Amount Paid:", results.getTotalPaid() + "");
//        text += genearateRow("Balance Left:", results.getAmountLeftToPay() + "");
//        text += "</table></body></html>";
//
//        return text;
//    }
//
//    /**
//     * Takes in a title and results and will return a string formated html table row
//     * with the title and the results within it.
//     *
//     * @param title
//     * @param result
//     * @return
//     */
//    private static String genearateRow(String title, String result) {
//        return  "<tr>" +
//                "<td>" + title + "</td>" +
//                "<td>" + result + "</td>" +
//                "</tr>";
//    }
}
