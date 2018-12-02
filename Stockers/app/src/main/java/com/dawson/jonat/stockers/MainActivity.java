package com.dawson.jonat.stockers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dawson.jonat.stockers.Notes.NoteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends Activity {


    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goToNoteActivity(View view) {
        Intent intent = new Intent(MainActivity.this, NoteActivity.class);
        startActivity(intent);
    }
    public void foreignExchangeClick(View v){
//        mAuth = FirebaseAuth.getInstance();
//        mAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                    mDatabase = FirebaseDatabase.getInstance().getReference();
//                    final Map<String, Hints> hints = new HashMap<>();
//                    hints.put("0", new Hints("Spending less that you earn is always a good financial plan. While living within your means isn’t always easy, it’s the best way to avoid excessive debt.","https://www.instagram.com/p/BqvL4ZtA95a/"));
//                    hints.put("1", new Hints("When signing up for a financial product or service, such as a credit card, you have the right to clear and simple information that does not mislead you. Never be afraid to ask questions before you agree to anything.","https://www.instagram.com/p/BqQK2gtnpK2/"));
//                    hints.put("2", new Hints("Debt got you down? Whether your debt is big or small, tackling it can feel overwhelming. With the right plan, you can start reducing both your debt and your financial stress.","https://www.instagram.com/p/Bp7rzZfgHan/"));
//                    hints.put("3", new Hints("With so many tempting #BlackFriday deals, it’s easy to get carried away and overspend! If you plan on shopping this Black Friday, having a budget will help you stay on track!","https://www.instagram.com/p/BqfshcDAJDJ/"));
//                    hints.put("4", new Hints("Credit card debt is giving the bank your money.  Pay your card in full every month or don’t use it","https://www.linkedin.com/in/pmcampbell/ "));
//                    hints.put("5", new Hints("Buy inexpensive, well diversified indexed mutual funds and exchange-traded funds","https://www.youtube.com/watch?v=JdUKhgW1gOo"));
//                    hints.put("6", new Hints("Make sure you are protected by insurance","https://www.youtube.com/watch?v=JdUKhgW1gOo"));
//                    hints.put("7", new Hints("Allocate at Least 20% of Your Income Toward Financial Priorities","https://www.themuse.com/advice/50-personal-finance-tips-that-will-change-the-way-you-think-about-money"));
//                    hints.put("8", new Hints("Avoid buying a car unless you use it everyday","https://www.quora.com/What-is-the-single-most-effective-piece-of-financial-advice-youve-ever-received"));
//                    hints.put("9", new Hints("Canada's tax system is horribly complex and many people miss opportunities to preserve or augment their finances because they are not familiar with all the details","https://www.theglobeandmail.com/globe-investor/investor-education/ten-money-tips-for-young-people/article4187137/"));
//                    mDatabase.child("hints").setValue(hints);
//                }
//            }
//        });
    }

    public void financialHintsClick(View v){

    }

    public void stockQuoteClick(View v){

    }

    public void notesClick(View v){

    }

    public void loanCalculatorClick(View v){

    }

    public void portfolioClick(View v){

    }

    public void messageClick(View v){

    }
}
