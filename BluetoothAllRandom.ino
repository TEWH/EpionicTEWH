#include <SoftwareSerial.h>


SoftwareSerial EEBlue(10, 11); // RX | TX
boolean happened= false;
void setup() {
  Serial.begin(9600);
  EEBlue.begin(9600);  //Default Baud for comm
  Serial.println("The bluetooth gates are open.\n Connect to HC-05 from any other bluetooth device with 1234 as pairing key!.");
 
}
     
void loop() {
  // Feed any data from bluetooth to Terminal.
  
  if (EEBlue.available())
    Serial.write(EEBlue.read());


    
  if (!happened) {
    //EEBlue.print("#55,34,78,22,39,43#72,29,33,44,23#503839,28,39,4985,47#92,21,24,29,33,23");
    String first = (String) (rand()%6);
    String second = (String) (rand()%5);
    String third = (String) (rand()%7);
    String fourth = (String) (rand()%8);
    String fifth = (String) (rand()%6);
    String sixth = (String) (rand()%7);
    
    //String data = "1#2#1#" + first + "," + second;
    //String data = "01#2#1#" + first;
   // String data = "~TP#1,2,34"; // need hashtag before *and* after
    String data = "~TP#" + first + "," + second + ",27";
    String data2 = "~BP#" + third + "," + fourth + ",81";
    String data3 = "~PO#" + fifth + "," + sixth + ",23";
    /*if ((data.length()>8)){
    Serial.println("The data greater than 8");
    }*/

    ///The delay is needed to ensure the first string is separate from the second, etc
    EEBlue.print(data);
    delay(100);
    EEBlue.print(data2);
    delay(100);
    EEBlue.print(data3);
  }
  //Serial.println(happened); -- was this giving the 1?
  int times = 500;
   delay(times);
    /*works with above, doesn't work when I'm just typing for some reason :x* -- but that
     * is fine. Only works when I type in my own 4 additional things, then does it based on
     * the above numbers;; using 2155 as second reading.. b/c it reconcatenates it with
     * itself
     */
  //  happened=true;
  
  
  

    
  // Feed all data from termial to bluetooth
//  if (Serial.available())
//  {
//    Serial.println("available");
//    //EEBlue.write(Serial.read());
//     
//      Serial.println(EEBlue.write(Serial.read())); 
//    
//    
//   }
    
  //}
  //EEBlue.write("hello");
}
