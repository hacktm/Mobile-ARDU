int leds[5][2]= {
  {10,19},
  {10,4},
  {10,5},
  {10,11},
  {10,2}
};
int cols[5] = {19,4,5,11,7};//high
int rows[7] = {10,18,9,6,2,8,3};//low
int i,m,n;
int small=7;
unsigned long lastComm=0;
// the setup function runs once when you press reset or power the board
void setup() {
  // initialize digital pin 13 as an output.
  Serial.begin(9600);
  for(i=0;i<=4;i++)
    pinMode(cols[i],OUTPUT);
  for(i=0;i<=6;i++)
    pinMode(rows[i],OUTPUT);
}

void resetAll(){
  for(i=0;i<=4;i++)
    digitalWrite(cols[i],LOW);
  for(i=0;i<=6;i++)
    digitalWrite(rows[i],LOW);
}

void lightLed(int col, int row, int duration=250)
{
  for(i=0;i<=6;i++)//light up all rows
    digitalWrite(rows[i],HIGH);
  
  digitalWrite(cols[col],HIGH);//ligh up column
  
  //digitalWrite(8,HIGH);
    
  //work pls
  digitalWrite(rows[row],LOW); 
  
  delay(duration);
  
  resetAll();
}

void animate(int a)
{
  switch(a)
  {
    case 8:
    lightLed(1,3,small);
    lightLed(2,3,small);
    lightLed(3,3,small);
    break;
    
    case 9:
    lightLed(1,3,small);
    lightLed(2,4,small);
    lightLed(3,5,small);
    break;
    
    case 6:
    lightLed(1,3,small);
    lightLed(1,4,small);
    lightLed(1,5,small);
    lightLed(1,6,small);
    break;
    
    case 7:
    lightLed(1,3,small);
    lightLed(2,2,small);
    lightLed(3,1,small);
    break;
    
    case 4:
    lightLed(1,3,small);
    lightLed(1,2,small);
    lightLed(1,1,small);
    lightLed(1,0,small);
    break;
    
    case 2:
    lightLed(1,2,small);
    lightLed(2,3,small);
    lightLed(3,4,small);
    lightLed(3,2,small);
    lightLed(2,3,small);
    lightLed(1,4,small);
    break;
    
    default:
      //lightLed(random(0,4),random(0,7),small);
    break;
  }
}

// the loop function runs over and over again forever
void loop() {
 if(Serial.available())
 {
    char c = Serial.read();
    int a = (int)c-(int)'0';
    
    Serial.print(a);Serial.print(" ");Serial.println((int)c);
    
    if(a==5)
    {
      for(int q=0;q<=90;q++)
        animate(2);
      for(int q=0;q<=90;q++)
        animate(4);
      for(int q=0;q<=90;q++)
        animate(7);
      for(int q=0;q<=90;q++)
        animate(8);
      for(int q=0;q<=90;q++)
        animate(9);
      for(int q=0;q<=90;q++)
        animate(6);
    }
    else
      for(int q=0;q<=90;q++)
      {
        animate(a);
      }
    
    lastComm = millis();
 }
 //if(millis()-lastComm)
   //animate(-1);
}
