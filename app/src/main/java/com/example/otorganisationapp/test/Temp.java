//package com.example.otorganisationapp;
//
//import android.util.Log;
//
//import com.example.otorganisationapp.domain.Condition;
//import com.example.otorganisationapp.domain.Patient;
//import com.example.otorganisationapp.domain.Session;
//import com.example.otorganisationapp.repository.OTDatabase;
//
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//
//public class StaticMethods {
//
//
//    public static String getFormattedDate(Date date) {
//
//        String formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH).format(date);
//
//        return formattedDate;
//
//    }
//
//    public static Date setFormattedDate(String date) {
//
//        Date formattedDate = null;
//
//        String dateTemplate = "dd/MM/yyyy";
//        DateFormat dateFormat = new SimpleDateFormat(dateTemplate, Locale.ENGLISH);
//
//        try {
//            formattedDate = dateFormat.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return formattedDate;
//
//    }
//
//    public static Condition[] createDummyConditionData() {
//
//        return new Condition[] {
//                new Condition("Alzheimers"),
//                new Condition("Gastroenteritis"),
//                new Condition("Stroke"),
//                new Condition("Coronavirus")
//        };
//    }
//
//    public static Patient[] createDummyPatientData() {
//
//        Date[] patientDates = getPatientDOBs();
//
//        return new Patient[] {
//                new Patient("Mr", "Alan Bucket", "Male", "1955417812", patientDates[0], "01209343122"),
//                new Patient("Mrs", "Isabelle Stroon", "Female", "6134601313", patientDates[1], "07515143211"),
//                new Patient("They", "Charlie Bell", "Prefer not to say","3135642777", patientDates[2] ,"07585233844" ),
//                new Patient("Miss", "Gwendoline Kristie", "Female","4443331213", patientDates[3] ,"0800232323" ),
//                new Patient("Mr", "Harvey Dunt", "Male","6000012310", patientDates[4] ,"06711456654" ),
//                new Patient("Ms", "Karen Brown", "Female","10043491233", patientDates[5] ,"01326212553") };
//    }
//
//    public static Session[] createDummySessionData(OTDatabase db) {
//
//        Date[] sessionDates = getSessionDates();
//
//
//        return new Session[]{
//                new Session(sessionDates[0], "Phone Call", "conversation about diagnosis & what meaningful occupations patient has", db.patientDAO().getPatientById(1).getPatientId())
//                new Session(sessionDates[1], "Face-to-Face", "Informal chat about life targets", db.patientDAO().getPatientById(2).getPatientId()),
//                new Session(sessionDates[2], "Face-to-Face", "Update on occupations and how patient can fit them around medical complaint", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[3], "Email", "Patient emailed requesting appointment for discussing occupations.", db.patientDAO().getPatientById(6).getPatientId()),
//                new Session(sessionDates[4], "Face-to-Face", "Patient experienced physical ailment & wishes to find a more mental occupation to focus on", db.patientDAO().getPatientById(5).getPatientId()),
//                new Session(sessionDates[5], "Phone Call", "catch-up on how patient is feeling. Appointment made for future session", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[6], "Face-to-Face", "Patient talking about occupations to involve family with new occupations.", db.patientDAO().getPatientById(6).getPatientId()),
//                new Session(sessionDates[7], "Face-to-Face", "Asked to see documents on diagnosis made by doctor - wants second opinion", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[8], "Email", "Sent email containing diagnosis", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[9], "Phone Call", "Patient cannot find something meaningful, discussion about improving mental health", db.patientDAO().getPatientById(5).getPatientId()),
//                new Session(sessionDates[10], "Face-to-Face", "Discussion on physical occupations and how they can be integrated.", db.patientDAO().getPatientById(6).getPatientId()),
//                new Session(sessionDates[11], "Phone Call", "Patient spoke excitedly about a new occupation they've discovered!", db.patientDAO().getPatientById(6).getPatientId()),
//                new Session(sessionDates[12], "SMS", "Patient messaged to confirm diagnosis was also reached by 3rd party", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[13], "Face-to-Face", "Patient noticeably worse; long conversation about finding new path", db.patientDAO().getPatientById(5).getPatientId()),
//                new Session(sessionDates[14], "Face-to-Face", "Patient chats about returning to work in stunt field - discussion about alternative paths", db.patientDAO().getPatientById(4).getPatientId()),
//                new Session(sessionDates[15], "Phone Call", "Patient calls to confirm treatment successful - thanks me for assisting with occupations", db.patientDAO().getPatientById(2).getPatientId()),
//                new Session(sessionDates[16], "Letter", "Patient requesting to be taken off system - unhappy about records.", db.patientDAO().getPatientById(3).getPatientId()),
//                new Session(sessionDates[17], "Face-to-face", "Patient deteriorating, conversation about finding easier occupations.", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[18], "Phone Call", "Patient has had an upturn! Chat about returning to previous occupations", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[19], "Email", "Doctor email - confirms patient has left the system. Unsure of whereabouts", db.patientDAO().getPatientById(5).getPatientId()),
//                new Session(sessionDates[20], "Phone Call", "Patient asks for help - unsure if is able to return to occupations now.", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[21], "Face-to-face", "patient has taken turn for the worst. Met within hospital, chatted casually.", db.patientDAO().getPatientById(1).getPatientId()),
//                new Session(sessionDates[22], "SMS", "patient & I spoke through text, mainly regarding their new occupations", db.patientDAO().getPatientById(6).getPatientId()),
//                new Session(sessionDates[23], "Email", "Doctor email - Patient now off system & appointments cancelled.", db.patientDAO().getPatientById(1).getPatientId())
//        };
//
//    }
//
//
//    public static void updatePatients(OTDatabase db) {
//
//
//        Patient p;
//        p = db.patientDAO().getPatientById(1);
//        p.setPrimaryComplaint(db.conditionDAO().getConditionById(1).getId());
//        p.setLivingSituation("Living at home with son Rory.");
//        p.setIsIndependentlyMobile(false);
//        p.setEquipment("Crutches");
//        db.patientDAO().updatePatient(p);
//
//        p = db.patientDAO().getPatientById(2);
//        p.setPrimaryComplaint(db.conditionDAO().getConditionById(2).getId());
//        p.setLivingSituation("Living with wife & kids at home");
//        p.setIsIndependentlyMobile(false);
//        p.setEquipment("");
//        db.patientDAO().updatePatient(p);
//
//        p = db.patientDAO().getPatientById(4);
//        db.patientDAO().getPatientById(4).setPrimaryComplaint(db.conditionDAO().getConditionById(3).getId());
//        db.patientDAO().getPatientById(3).setLivingSituation("Moved into sister's flat after complaint became present and too hard to maintain.");
//        db.patientDAO().getPatientById(3).setIsIndependentlyMobile(false);
//        db.patientDAO().getPatientById(3).setEquipment("Wheelchair, electric stair case chair");
//        db.patientDAO().updatePatient(p);
//
//        p = db.patientDAO().getPatientById(5);
//        db.patientDAO().getPatientById(4).setPrimaryComplaint(db.conditionDAO().getConditionById(4).getId());
//        p.setPrimaryComplaint(db.conditionDAO().getConditionById(4).getId());
//        p.setLivingSituation("has no permanent address,  moving around a lot (has been advised not to)");
//        p.setIsIndependentlyMobile(false);
//        p.setEquipment("");
//        db.patientDAO().updatePatient(p);
//
//        db.sessionDAO().insertSession(
//
//
//        );
//    }
//
//
//
//
//    private static Date[] getPatientDOBs() {
//
//        Calendar calendar;
//
//        Date date1;
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.DATE, 30);
//        calendar.set(Calendar.YEAR, 1955);
//        date1 = calendar.getTime();
//
//        Date date2;
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 8);
//        calendar.set(Calendar.DATE, 24);
//        calendar.set(Calendar.YEAR, 1982);
//        date2 = calendar.getTime();
//
//        Date date3;
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 2);
//        calendar.set(Calendar.DATE, 19);
//        calendar.set(Calendar.YEAR, 1971);
//        date3 = calendar.getTime();
//
//        Date date4;
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 7);
//        calendar.set(Calendar.DATE, 14);
//        calendar.set(Calendar.YEAR, 1946);
//        date4 = calendar.getTime();
//
//        Date date5;
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 11);
//        calendar.set(Calendar.DATE, 1);
//        calendar.set(Calendar.YEAR, 2000);
//        date5 = calendar.getTime();
//
//        Date date6;
//        calendar = Calendar.getInstance();
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.DATE, 3);
//        calendar.set(Calendar.YEAR, 1979);
//        date6 = calendar.getTime();
//
//
//        return new Date[] {date1, date2, date3, date4, date5, date6};
//
//    }
//
//    private static Date[] getSessionDates() {
//
//        Calendar calendar = Calendar.getInstance();
//
//        Date alan1;
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.DATE, 14);
//        calendar.set(Calendar.YEAR, 2020);
//        alan1 = calendar.getTime();
//
//        Date alan2;
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.DATE, 21);
//        calendar.set(Calendar.YEAR, 2020);
//        alan2 = calendar.getTime();
//
//        Date alan3;
//        calendar.set(Calendar.MONTH, 1);
//        calendar.set(Calendar.DATE, 2);
//        calendar.set(Calendar.YEAR, 2020);
//        alan3 = calendar.getTime();
//
//        Date alan4;
//        calendar.set(Calendar.MONTH, 2);
//        calendar.set(Calendar.DATE, 7);
//        calendar.set(Calendar.YEAR, 2020);
//        alan4 = calendar.getTime();
//
//        Date alan5;
//        calendar.set(Calendar.MONTH, 2);
//        calendar.set(Calendar.DATE, 13);
//        calendar.set(Calendar.YEAR, 2020);
//        alan5 = calendar.getTime();
//
//        Date alan6;
//        calendar.set(Calendar.MONTH, 2);
//        calendar.set(Calendar.DATE, 18);
//        calendar.set(Calendar.YEAR, 2020);
//        alan6 = calendar.getTime();
//
//        Date alan7;
//        calendar.set(Calendar.MONTH, 3);
//        calendar.set(Calendar.DATE, 1);
//        calendar.set(Calendar.YEAR, 2020);
//        alan7 = calendar.getTime();
//
//        Date alan8;
//        calendar.set(Calendar.MONTH, 3);
//        calendar.set(Calendar.DATE, 8);
//        calendar.set(Calendar.YEAR, 2020);
//        alan8 = calendar.getTime();
//
//        Date isabelle1;
//        calendar.set(Calendar.MONTH, 3);
//        calendar.set(Calendar.DATE, 11);
//        calendar.set(Calendar.YEAR, 2020);
//        isabelle1 = calendar.getTime();
//
//        Date isabelle2;
//        calendar.set(Calendar.MONTH, 4);
//        calendar.set(Calendar.DATE, 13);
//        calendar.set(Calendar.YEAR, 2020);
//        isabelle2 = calendar.getTime();
//
//        Date karen1;
//        calendar.set(Calendar.MONTH, 4);
//        calendar.set(Calendar.DATE, 15);
//        calendar.set(Calendar.YEAR, 2020);
//        karen1 = calendar.getTime();
//
//        Date karen2;
//        calendar.set(Calendar.MONTH, 4);
//        calendar.set(Calendar.DATE, 19);
//        calendar.set(Calendar.YEAR, 2020);
//        karen2 = calendar.getTime();
//
//        Date karen3;
//        calendar.set(Calendar.MONTH, 5);
//        calendar.set(Calendar.DATE, 26);
//        calendar.set(Calendar.YEAR, 2020);
//        karen3 = calendar.getTime();
//
//        Date karen4;
//        calendar.set(Calendar.MONTH, 5);
//        calendar.set(Calendar.DATE, 30);
//        calendar.set(Calendar.YEAR, 2020);
//        karen4 = calendar.getTime();
//
//        Date harvey1;
//        calendar.set(Calendar.MONTH, 6);
//        calendar.set(Calendar.DATE, 7);
//        calendar.set(Calendar.YEAR, 2020);
//        harvey1 = calendar.getTime();
//
//        Date harvey2;
//        calendar.set(Calendar.MONTH, 6);
//        calendar.set(Calendar.DATE, 12);
//        calendar.set(Calendar.YEAR, 2020);
//        harvey2 = calendar.getTime();
//
//        Date harvey3;
//        calendar.set(Calendar.MONTH, 7);
//        calendar.set(Calendar.DATE, 1);
//        calendar.set(Calendar.YEAR, 2020);
//        harvey3 = calendar.getTime();
//
//        Date gwendoline1;
//        calendar.set(Calendar.MONTH, 8);
//        calendar.set(Calendar.DATE, 5);
//        calendar.set(Calendar.YEAR, 2020);
//        gwendoline1 = calendar.getTime();
//
//        Date gwendoline2;
//        calendar.set(Calendar.MONTH, 8);
//        calendar.set(Calendar.DATE, 15);
//        calendar.set(Calendar.YEAR, 2020);
//        gwendoline2 = calendar.getTime();
//
//        Date charlie1;
//        calendar.set(Calendar.MONTH, 9);
//        calendar.set(Calendar.DATE, 17);
//        calendar.set(Calendar.YEAR, 2020);
//        charlie1 = calendar.getTime();
//
//        Date alan9;
//        calendar.set(Calendar.MONTH, 10);
//        calendar.set(Calendar.DATE, 4);
//        calendar.set(Calendar.YEAR, 2020);
//        alan9 = calendar.getTime();
//
//        Date alan10;
//        calendar.set(Calendar.MONTH, 10);
//        calendar.set(Calendar.DATE, 18);
//        calendar.set(Calendar.YEAR, 2020);
//        alan10 = calendar.getTime();
//
//        Date karen5;
//        calendar.set(Calendar.MONTH, 10);
//        calendar.set(Calendar.DATE, 27);
//        calendar.set(Calendar.YEAR, 2020);
//        karen5 = calendar.getTime();
//
//        Date alan11;
//        calendar.set(Calendar.MONTH, 11);
//        calendar.set(Calendar.DATE, 24);
//        calendar.set(Calendar.YEAR, 2020);
//        alan11 = calendar.getTime();
//
//
//        return new Date[] {alan1, isabelle1, alan2, karen1, harvey1, alan3, karen2, alan4, alan5, harvey2,
//                karen3, karen4, alan6, harvey3, gwendoline1, isabelle2, charlie1, alan7, alan8, gwendoline2, alan9, alan10, karen5, alan11};
//
//
//    }
//}
