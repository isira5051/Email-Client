import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmailClient {


public static void main(String[] args) {

////create all the objects///////
ArrayList<birthdayList> list = new ArrayList<>();
reception reception = new reception();
reception.fillDetails();
Scanner input = new Scanner(System.in);

////create all the objects//////

getTimeAndSendBirthdayWishes time = new getTimeAndSendBirthdayWishes();
otherProcess t3 = new otherProcess();
String today = t3.getTodayDate();

list = reception.getBirthdayRecipientsForTheDay(today);
time.process(list , today);





System.out.println("Enter option type: \n"
+ "1 - Adding a new recipient\n"
+ "2 - Sending an email\n"
+ "3 - Printing out all the recipients who have birthdays\n"
+ "4 - Printing out details of all the emails sent\n"
+ "5 - Printing out the number of recipient objects in the application\n" );

int option = input.nextInt();



switch(option){
case 1:



input.nextLine();
AddNewRecord newRec = new AddNewRecord();
String getInput = input.nextLine();
newRec.addToFile(getInput);


// input format - Official: nimal,nimal@gmail.com,ceo
// Use a single input to get all the details of a recipient


// Hint: use methods for reading and writing files
break;
case 2:


otherProcess t1 = new otherProcess();
t1.get_email();

// input format - email, subject, content
// code to send an email
break;

case 3:
input.nextLine();


String input_date = input.nextLine();
ArrayList<birthdayList> list2 = new ArrayList<>();
list2 = reception.getBirthdayRecipientsForTheDay(input_date);

for (int i = 0 ; i < list2.size() ; i ++)
{
System.out.println(list2.get(i).name);
}



break;

case 4:

// input format - yyyy/MM/dd (ex: 2018/09/17)
// code to print the details of all the emails sent on the input date

input.nextLine();
String findDate = input.next();
otherProcess T = new otherProcess();
T.read_emails(findDate);
break;

case 5:


input.nextLine();
store_recipients_items_details st2 = new store_recipients_items_details();
st2.scan_file();
st2.countRecords();


break;

}

// start email client
// code to create objects for each recipient in clientList.txt
// use necessary variables, methods and classes

}
}

class getTimeAndSendBirthdayWishes//get today date and find who have birthdays and send wishes
{


void process(ArrayList<birthdayList>list , String today)//takes birthday list of the day
{
try

{
File file3 = new File("storeDate.txt");//store date ina temp.file
Scanner scan2 = new Scanner(file3);

boolean run = true;
try
{
if (scan2.nextLine().equals(today))//if we sent emails today dont run the program
  {
  run = false;
  }
}
catch (Exception e)
{

} // catch the error if it has no lines to read

if (run)//if we didnt run the program today

{
FileWriter writer2 = new FileWriter("storeDate.txt");
if(scan2.hasNextLine())//delete the date from the file//clear file and get the new date
{

PrintWriter writer4 = new PrintWriter(file3);
writer4.print("");
writer4.close();

}
writer2.write(today);//write today date
sendBirthdayWish wish = new sendBirthdayWish();//to send wish!

wish.sendWish(today,list);
writer2.close();
}

}
catch(Exception e)
{
System.out.println(e);
}


System.out.println();
System.out.println();

}



}



class sendBirthdayWish
{

void sendWish(String today , ArrayList<birthdayList> birthday_list_today)//takes today date and birthday list
{

otherProcess sendBirthdayMail = new otherProcess();
reception r2 = new reception();
for (int i = 0 ; i < birthday_list_today.size() ; i ++)// we store objects as birthday recipients attributes
{ //are birthday,email,status,we need status to know the
String email = birthday_list_today.get(i).email; //birthday wish type
String status = birthday_list_today.get(i).getStatus();
String emailParts [] = sendBirthdayMail.sendBirthdayWish(email,status);//store email structure to an array

try
{
sendBirthdayMail.write(emailParts);//for serialization
sendBirthdayMail.send_email(emailParts);


}
catch(Exception e)

{
System.out.println(e);
}


}

}

}


abstract class recipients_list // superclass
{
protected String name;
protected String email;


}
//inheritance
//getters
class birthdayList extends recipients_list
{

private String status;
public birthdayList(String name , String email, String status)
{
this.name = name;
this.email = email;
this.status = status;

}

String getStatus()
{
return status;
}

}

class officeRecipients extends recipients_list
{

private String pos;


public officeRecipients(String name , String email , String pos)
{
this.name = name;
this.email = email;
this.pos = pos;
}

String getName()
{
return name;
}

String getEmail()
{
return email;
}
String getPos()
{
return pos;
}


}

class officeFriends extends recipients_list
{

private String birthday;
private String pos;

public officeFriends(String name , String email , String pos , String birthday)
{

this.name = name;
this.email = email;
this.pos = pos;
this.birthday = birthday;
}

String getName()
{
return name;

}

String getEmail()
{
return email;
}
String getPos()
{
return pos;
}
String getBirthday()
{
return birthday;
}

}

class personalFriends extends recipients_list
{

private String nickNmae;
private String birthday;

public personalFriends(String name , String nickname , String email, String birthday )
{
this.name = name;
this.email = email;
this.nickNmae = nickname;
this.birthday = birthday;

}

String getName()
{
return name;

}
String getNickNmae()
{
return nickNmae;
}

String getEmail()
{
return email;
}

String getBirthday()
{
return birthday;
}

}



class reception //to create all objects
{

ArrayList<officeFriends> officeFriends = new ArrayList<>();
ArrayList<personalFriends> personalFriends = new ArrayList<>();




void fillDetails() // create objects for all the recipients
{

store_recipients_items_details storeDetails = new store_recipients_items_details();
storeDetails.scan_file();
storeDetails.split();
officeFriends = storeDetails.officeFriends;
personalFriends = storeDetails.personalFriends;

}


ArrayList<birthdayList> getBirthdayRecipientsForTheDay(String today)
{

ArrayList<birthdayList> birthdayListOfTheDay = new ArrayList<>();
String s3 [];
s3 = today.split("/");
for (int i = 0 ; i < officeFriends.size() ; i ++) //iterate
{
String s2[]; // temp array
//check for officefriends
s2 = officeFriends.get(i).getBirthday().split("/");

if (s3[1].equals(s2[1]) && s3[2].equals(s2[2]))
{
String type = "Office_friend";
birthdayListOfTheDay.add(new birthdayList(officeFriends.get(i).getName() , officeFriends.get(i).getEmai
l() , type));


}
}

for (int i = 0 ; i < personalFriends.size() ; i ++)
{
String s2[];// temp array
//check for personalFriends
s2 = personalFriends.get(i).getBirthday().split("/");

if (s3[1].equals(s2[1]) && s3[2].equals(s2[2]))
{
String type = "Personal";
birthdayListOfTheDay.add(new birthdayList(personalFriends.get(i).name , personalFriends.get(i).email , t
ype));


}
}

return birthdayListOfTheDay;
}


}



class AddNewRecord// to create a record of a new recipient
{

void addToFile(String getInput)
{

try
{
// code to add a new recipient


Scanner input = new Scanner(System.in);
FileWriter writer = new FileWriter("recipientListDetails.txt ", true);
// store details in clientList.txt file
writer.write(getInput+"\n");
writer.close();

}
catch(Exception e)
{
System.out.println(e);
}

}

}

class otherProcess ////// for other processes/////

{

ArrayList<Recipients> list = new ArrayList<Recipients>();//we will store the birthday details here
String sender_mail = null;
String subject = null;
String content_mail = null;

String getTodayDate()//get today date
{
Date date=new Date();
SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
String today = f.format(date);
return today;
}


String [] sendBirthdayWish(String email , String status)// send the birthday wish by considering the status
{ // personal or official

String subject = null;
String content = null;

if (status.equals("Personal"))
{

subject = "Happy Birthday!";
content = " hugs and love on your birthday. from Isira Induwara";

}

else if (status.equals("Office_friend"))
{
subject = "Happy Birthday!";
content = "Wish you a Happy Birthday. Isira Induwara";

}

String emailParts [] = {email, subject , content};
return emailParts;

}


void get_email()
{


Scanner input = new Scanner(System.in);
String s = input.nextLine();

String emailParts [] = new String[4]; // temp array to store email structure

emailParts = s.split(",");
write(emailParts);
try
{
send_email(emailParts);
}
catch(Exception e)
{
System.out.println(e);
}

}

void send_email(String [] emailParts) throws Exception // send the mail
{

send_mail_to send = new send_mail_to();



send.sendMail(emailParts[0] , emailParts[1] , emailParts[2] );



}

void deserialize()
{


File file1 = new File("new.txt");
if (list.size() == 0)
{
try
{

File file = new File("new.txt");
FileInputStream fis = new FileInputStream(file);
ObjectInputStream ois = new ObjectInputStream(fis);
ArrayList<Recipients> List = (ArrayList<Recipients>)ois.readObject();

list = List;

}
catch(Exception e)
{
//System.out.println(e);
}
}

}
void read_emails(String date) {

deserialize();


if(list.size() > 0)
{

for (int i = 0; i < list.size(); i++)
{

if (date.equals(list.get(i).date))
{
System.out.println(list.get(i).email);
System.out.println(list.get(i).subject);
System.out.println();
}


}
}

}



void write(String emailParts[]) // write to the text file
{
deserialize();
String today_date = getTodayDate();
list.add(new Recipients( emailParts[0] , emailParts[1] , emailParts[2] , today_date)) ;

try
{

File file = new File("new.txt");
FileOutputStream fos = new FileOutputStream(file);
ObjectOutputStream oos = new ObjectOutputStream(fos);
oos.writeObject(list);


}
catch(Exception e)
{
System.out.println(e);
}

}

}


class Recipients implements Serializable
{

protected String email;

String subject;
String content;
String date;

public Recipients(String email , String subject, String content , String date)
{

this.email = email;
this.subject = subject;
this.content = content;
this.date = date;
}

}


class send_mail_to /////send mail ////////
{
public static void sendMail(String senders_mail , String subject , String content) throws Exception {

System.out.println("in progress !");

Properties prop = new Properties();
prop.put("mail.smtp.auth", "true");
prop.put("mail.smtp.starttls.enable", "true");
prop.put("mail.smtp.host", "smtp.gmail.com");
// prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
prop.put("mail.smtp.port", "587");

String username = "induwarasamarasinghe5051@gmail.com";
String password = "xxodbanvwjzttghc";

Session session = Session.getInstance(prop, new Authenticator() {
protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication(username, password);
}
});

Message message = prepareMessage(session, username, senders_mail , subject, content );
Transport.send(message);
System.out.println("Email Sent Successfully!");
}

private static Message prepareMessage(Session session, String myEmail, String senders_mail , String subject , Str
ing content)
{
try {
Message message = new MimeMessage(session);
message.setFrom(new InternetAddress("induwarasamarasinghe5051@gmail.com"));
message.setRecipients(
Message.RecipientType.TO,
InternetAddress.parse(senders_mail));
message.setSubject(subject);
message.setText(content);
return message;
} catch (Exception e) {
Logger.getLogger(JavaMail.class.getName()).log(Level.SEVERE, null, e);
}
return null;
}

}




class store_recipients_items_details
{

ArrayList<String> list = new ArrayList<String>(); //to store the text that is split from the file

ArrayList<personalFriends> personalFriends = new ArrayList<>();
ArrayList<officeRecipients>officeRecipients = new ArrayList<>();
ArrayList<officeFriends> officeFriends = new ArrayList<>();



void split()
{


for (int i = 0 ; i < list.size() ; i ++)
{

String res[] = list.get(i).split("[,]", 0);


String intro = res[0];
String email = res[1];
String pos = res[2];
String birthday = null;

String temp_s [] ;
temp_s = res[0].split(": ");

if (res.length > 3)
{

birthday = res[3];
}

if (temp_s[0].equals("Personal"))
{
personalFriends.add(new personalFriends(temp_s[1] , res[1] , pos , birthday));

}
else if (temp_s[0].equals("Official"))
{
officeRecipients.add(new officeRecipients(temp_s[1] , res[1] , pos ));

}

else if (temp_s[0].equals("Office_friend"))
{
officeFriends.add(new officeFriends(temp_s[1] , res[1] , pos , birthday ));

}
}

}

void countRecords()//count all the records
{
System.out.println(list.size());

}

void scan_file()//read from the file
{



File file = new File("recipientListDetails.txt");

try
{
Scanner scan = new Scanner(file);

while(scan.hasNextLine())
{

list.add(scan.nextLine());
}

}
catch (Exception e)
{
System.out.println(e);
}

}



}





 
