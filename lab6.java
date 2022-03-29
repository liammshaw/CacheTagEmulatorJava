import java.util.*;
import java.io.*;
import java.security.Timestamp;
import java.text.ParsePosition;

public class lab6{
   public static void main(String[] args) {
      try{
         Scanner inFile = new Scanner(new File(args[0]));
         ArrayList<Integer> addresses = new ArrayList<Integer>();
         while(inFile.hasNextLine()){
            String[] line = inFile.nextLine().split("\t");
            addresses.add(Integer.parseInt(line[1], 16));
         }
         TwoKB_DirectMapped_OneWordBlock(addresses);
         TwoKB_DirectMapped_TwoWordBlock(addresses);
         TwoKB_DirectMapped_FourWordBlock(addresses);
         TwoKB_TwoWay_OneWordBlock(addresses);
         TwoKB_FourWay_OneWordBlock(addresses);
         TwoKB_FourWay_FourWordBlock(addresses);
         FourKB_DirectMapped_OneWordBlock(addresses);
      }
      catch(IOException e){
         System.out.println("File Not Found");
      }
   }

   public static void TwoKB_DirectMapped_OneWordBlock(ArrayList<Integer> addresses){
      Tag[] cache = new Tag[512];
      int counter = 0;
      int hits = 0;
      for(int address : addresses){
         address = address >>> 2;
         int index = address & 511;
         int tag =  address >>> 9;
         if(cache[index] == null){
            cache[index] = new Tag(counter, tag);
         }
         else if(cache[index].tag == tag){
            cache[index].timeStamp = counter;
            hits++;
         }
         else{
            cache[index] = new Tag(counter, tag);
         }
         counter++;
      }
      System.out.println("Cache #1");
      System.out.println("Cache size: 2048B\tAssociativity: 1\tBlock size: 1");
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", hits, (double)(Math.round((double)hits/counter*10000))/100);
      System.out.println("---------------------------");
   }

   public static void TwoKB_DirectMapped_TwoWordBlock(ArrayList<Integer> addresses){
      Tag[] cache = new Tag[256];
      int counter = 0;
      int hits = 0;
      for(int address : addresses){
         address = address >>> 3;
         int index = address & 255;
         int tag =  address >>> 8;
         if(cache[index] == null){
            cache[index] = new Tag(counter, tag);
         }
         else if(cache[index].tag == tag){
            cache[index].timeStamp = counter;
            hits++;
         }
         else{
            cache[index] = new Tag(counter, tag);
         }
         counter++;
      }
      System.out.println("Cache #2");
      System.out.println("Cache size: 2048B\tAssociativity: 1\tBlock size: 2");
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", hits, (double)(Math.round((double)hits/counter*10000))/100);
      System.out.println("---------------------------");
   }

   public static void TwoKB_DirectMapped_FourWordBlock(ArrayList<Integer> addresses){
      Tag[] cache = new Tag[128];
      int counter = 0;
      int hits = 0;
      for(int address : addresses){
         address = address >>> 4;
         int index = address & 127;
         int tag =  address >>> 7;
         if(cache[index] == null){
            cache[index] = new Tag(counter, tag);
         }
         else if(cache[index].tag == tag){
            cache[index].timeStamp = counter;
            hits++;
         }
         else{
            cache[index] = new Tag(counter, tag);
         }
         counter++;
      }
      System.out.println("Cache #3");
      System.out.println("Cache size: 2048B\tAssociativity: 1\tBlock size: 4");
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", hits, (double)(Math.round((double)hits/counter*10000))/100);
      System.out.println("---------------------------");
   }

   public static void TwoKB_TwoWay_OneWordBlock(ArrayList<Integer> addresses){
      Tag[] cache1 = new Tag[256];
      Tag[] cache2 = new Tag[256];
      int counter = 0;
      int hits = 0;
      for(int address : addresses){
         address = address >>> 2;
         int index = address & 255;
         int tag =  address >>> 8;
         if(cache1[index] == null){
            cache1[index] = new Tag(counter, tag);
         }
         else if(cache1[index].tag == tag){
            cache1[index].timeStamp = counter;
            hits++;
         }
         else if(cache2[index] == null){
            cache2[index] = new Tag(counter, tag);
         }
         else if(cache2[index].tag == tag){
            cache2[index].timeStamp = counter;
            hits++;
         }
         else{
            if(cache1[index].timeStamp < cache2[index].timeStamp){
               cache1[index] = new Tag(counter, tag);
            }
            else{
               cache2[index] = new Tag(counter, tag);
            }
         }
         counter++;
      }
      System.out.println("Cache #4");
      System.out.println("Cache size: 2048B\tAssociativity: 2\tBlock size: 1");
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", hits, (double)(Math.round((double)hits/counter*10000))/100);
      System.out.println("---------------------------");
   }

   public static void TwoKB_FourWay_OneWordBlock(ArrayList<Integer> addresses){
      Tag[] cache1 = new Tag[128];
      Tag[] cache2 = new Tag[128];
      Tag[] cache3 = new Tag[128];
      Tag[] cache4 = new Tag[128];
      int counter = 0;
      int hits = 0;
      for(int address : addresses){
         address = address >>> 2;
         int index = address & 127;
         int tag =  address >>> 7;
         if(cache1[index] == null){
            cache1[index] = new Tag(counter, tag);
         }
         else if(cache1[index].tag == tag){
            cache1[index].timeStamp = counter;
            hits++;
         }
         else if(cache2[index] == null){
            cache2[index] = new Tag(counter, tag);
         }
         else if(cache2[index].tag == tag){
            cache2[index].timeStamp = counter;
            hits++;
         }
         else if(cache3[index] == null){
            cache3[index] = new Tag(counter, tag);
         }
         else if(cache3[index].tag == tag){
            cache3[index].timeStamp = counter;
            hits++;
         }
         else if(cache4[index] == null){
            cache4[index] = new Tag(counter, tag);
         }
         else if(cache4[index].tag == tag){
            cache4[index].timeStamp = counter;
            hits++;
         }
         else{
            int[] timeStamps = new int[4];
            timeStamps[0] = cache1[index].timeStamp;
            timeStamps[1] = cache2[index].timeStamp;
            timeStamps[2] = cache3[index].timeStamp;
            timeStamps[3] = cache4[index].timeStamp;
            int min = getMin(timeStamps);
            if(min == 0){
               cache1[index] = new Tag(counter, tag);
            }
            else if(min == 1){
               cache2[index] = new Tag(counter, tag);
            }
            else if(min == 2){
               cache3[index] = new Tag(counter, tag);
            }
            else{
               cache4[index] = new Tag(counter, tag);
            }

         }
         counter++;
      }
      System.out.println("Cache #5");
      System.out.println("Cache size: 2048B\tAssociativity: 4\tBlock size: 1");
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", hits, (double)(Math.round((double)hits/counter*10000))/100);
      System.out.println("---------------------------");
   }

   public static void TwoKB_FourWay_FourWordBlock(ArrayList<Integer> addresses){
      Tag[] cache1 = new Tag[32];
      Tag[] cache2 = new Tag[32];
      Tag[] cache3 = new Tag[32];
      Tag[] cache4 = new Tag[32];
      int counter = 0;
      int hits = 0;
      for(int address : addresses){
         address = address >>> 4;
         int index = address & 31;
         int tag =  address >>> 5;
         if(cache1[index] == null){
            cache1[index] = new Tag(counter, tag);
         }
         else if(cache1[index].tag == tag){
            cache1[index].timeStamp = counter;
            hits++;
         }
         else if(cache2[index] == null){
            cache2[index] = new Tag(counter, tag);
         }
         else if(cache2[index].tag == tag){
            cache2[index].timeStamp = counter;
            hits++;
         }
         else if(cache3[index] == null){
            cache3[index] = new Tag(counter, tag);
         }
         else if(cache3[index].tag == tag){
            cache3[index].timeStamp = counter;
            hits++;
         }
         else if(cache4[index] == null){
            cache4[index] = new Tag(counter, tag);
         }
         else if(cache4[index].tag == tag){
            cache4[index].timeStamp = counter;
            hits++;
         }
         else{
            int[] timeStamps = new int[4];
            timeStamps[0] = cache1[index].timeStamp;
            timeStamps[1] = cache2[index].timeStamp;
            timeStamps[2] = cache3[index].timeStamp;
            timeStamps[3] = cache4[index].timeStamp;
            int min = getMin(timeStamps);
            if(min == 0){
               cache1[index] = new Tag(counter, tag);
            }
            else if(min == 1){
               cache2[index] = new Tag(counter, tag);
            }
            else if(min == 2){
               cache3[index] = new Tag(counter, tag);
            }
            else{
               cache4[index] = new Tag(counter, tag);
            }

         }
         counter++;
      }
      System.out.println("Cache #6");
      System.out.println("Cache size: 2048B\tAssociativity: 4\tBlock size: 4");
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", hits, (double)(Math.round((double)hits/counter*10000))/100);
      System.out.println("---------------------------");
   }

   public static int getMin(int[] timeStamps){
      int index = 0;
      int min = timeStamps[0];
      for(int i = 1; i < timeStamps.length; i++){
         if(timeStamps[i] < min){
            index = i;
            min = timeStamps[i];
         }
      }
      return index;
   }

   public static void FourKB_DirectMapped_OneWordBlock(ArrayList<Integer> addresses){
      Tag[] cache = new Tag[1024];
      int counter = 0;
      int hits = 0;
      for(int address : addresses){
         address = address >>> 2;
         int index = address & 1023;
         int tag =  address >>> 10;
         if(cache[index] == null){
            cache[index] = new Tag(counter, tag);
         }
         else if(cache[index].tag == tag){
            cache[index].timeStamp = counter;
            hits++;
         }
         else{
            cache[index] = new Tag(counter, tag);
         }
         counter++;
      }
      System.out.println("Cache #7");
      System.out.println("Cache size: 4096B\tAssociativity: 1\tBlock size: 1");
      System.out.printf("Hits: %d\tHit Rate: %.2f%%\n", hits, (double)(Math.round((double)hits/counter*10000))/100);
      System.out.println("---------------------------");
   }

   public static class Tag{
      public int timeStamp;
      public int tag;
   
      public Tag(int s, int t){
         tag = t;
         timeStamp = s;
      }
   }
}
