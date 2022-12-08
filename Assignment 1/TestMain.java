package assignments2021.a1;

import java.util.LinkedList;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

class Sol_Get_Int extends Memory{

    public Sol_Get_Int(int n) {
        super(n);
        idCount = 0;
    }

    public int get(String s) {
        String sm;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                return tempInterval.id;
            }
        }
        return -1;
    }

    public int remove(String s) {
        String sm;
        int index = 0;
        int id = -1;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                id = tempInterval.id;
                break;
            }
            else
                index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
        }
        return id;
    }

    public String remove(int id) {

        //  Maybe rewrite this one to return -1 if the id doesn't exist and 1 if it does.
        //  Or return the string?

        int index = 0;
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s += memoryArray[tempInterval.start + i];
                }
                break;
            }
            index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
            return s;

        }
        return null;
    }

    //  Normally we would make this private.  But for grading purposes, we can make it public.

    public void defragment() {
        int memoryIndex = 0;
        for (Memory.StringInterval tempInterval : intervalList) {
            for (int i=0; i < tempInterval.length; i++)
                memoryArray[memoryIndex + i] = memoryArray[tempInterval.start + i];
            tempInterval.start = memoryIndex;
            memoryIndex += tempInterval.length;
        }
    }

    public int put(String s) {

        //  First check if there are any strings in memory.  If yes, then we can add the string.
        //  Here I don't bother checking that the string can fit into empty memory, but I could add that.

        int len = s.length();
        if  (intervalList.size() == 0) {  //  no string intervals in memory
            if (len <= memoryArray.length){
                Memory.StringInterval interval = new Memory.StringInterval(idCount, 0, s.length());
                intervalList.add(interval);
                for (int i = 0; i < len; i++) {
                    memoryArray[i] = s.charAt(i);
                }
                return idCount;
            }
        }
        else{   // memory is not empty.

            int stringIntervalIndex = 0;
            int tempStart;
            int endOfLast = -1;    //  This will be the index of the last character of the previous interval.
            //  The next gap will start at endOfLast+1.
            boolean found = false;

            /*
             * Step through the string interval list and, for each string interval,
             * check if there is a gap *before* that interval.
             * The gap size is the start of the current interval minus endOfLast.
             */

            for (Memory.StringInterval tempInterval : intervalList) {
                tempStart = tempInterval.start;
                if (endOfLast + len < tempStart) {  //  we've found a viable gap before the start of tempInterval.
                    //   tempStart will be the starting location
                    found = true;
                    break;
                }
                else {   //  the gap is not big enough for the current string.  So move on to the next one.
                    //  end of last will be the last index of the current interval
                    endOfLast = tempInterval.start + tempInterval.length - 1;
                    stringIntervalIndex++;
                }
            }
            /*
             *   If there is no room before an interval, then there may still be room after the last interval.
             *   Note that that stringIntervalIndex will be one greater than the list index of the final string,
             *   and so it has the correct value for inserting a new string interval.
             */

            if (!found) {   // endOfLast + len would be the index of last char, in case there is room *after* the last string interval.
                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                };
            }

            if (!found) {  //  If there was no room found, then defragment and check if there is room at the end of the array
                defragment();   //  after this method runs,  all intervals have been shifted and the only gap is at the end.
                //  so check if this gap is big enough for the new string
                Memory.StringInterval interval = intervalList.getLast();
                endOfLast =  interval.start + interval.length - 1;

                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                }
            }

            /*
             * All possible ways of finding a gap have been tested. If we've found a gap,
             * then we can
             * - insert the characters
             * - increment the idCount
             * - add a string interval object to the list at the appropriate place
             */

            if (found) {
                //   write the characters in memory
                for (int i = 0; i < len; i++) {
                    memoryArray[endOfLast + 1 + i] = s.charAt(i);
                }
                //   add a new interval to the list
                idCount++;
                Memory.StringInterval interval = new Memory.StringInterval(idCount, endOfLast+1, s.length());

                /*
                 * By using the Java LinkedList class, students can avoid the headaches of implementing their own (and getting it wrong).
                 * The interval will be added before the current interval if a gap was found before some string interval,
                 * or else it will be added after the last interval.
                 */

                intervalList.add(stringIntervalIndex,interval);

                return idCount;
            }
        }
        return -1;
    }
}

class Sol_Remove_Int extends Memory{

    public Sol_Remove_Int(int n) {
        super(n);
        idCount = 0;
    }

    public String get(int id) {
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s = s + memoryArray[tempInterval.start + i];
                }
                return s;
            }
        }
        return null;
    }

    public int get(String s) {
        String sm;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                return tempInterval.id;
            }
        }
        return -1;
    }

    public int remove(String s) {
        String sm;
        int index = 0;
        int id = -1;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                id = tempInterval.id;
                break;
            }
            else
                index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
        }
        return id;
    }

    public void defragment() {
        int memoryIndex = 0;
        for (Memory.StringInterval tempInterval : intervalList) {
            for (int i=0; i < tempInterval.length; i++)
                memoryArray[memoryIndex + i] = memoryArray[tempInterval.start + i];
            tempInterval.start = memoryIndex;
            memoryIndex += tempInterval.length;
        }
    }

    public int put(String s) {

        //  First check if there are any strings in memory.  If yes, then we can add the string.
        //  Here I don't bother checking that the string can fit into empty memory, but I could add that.

        int len = s.length();
        if  (intervalList.size() == 0) {  //  no string intervals in memory
            if (len <= memoryArray.length){
                Memory.StringInterval interval = new Memory.StringInterval(idCount, 0, s.length());
                intervalList.add(interval);
                for (int i = 0; i < len; i++) {
                    memoryArray[i] = s.charAt(i);
                }
                return idCount;
            }
        }
        else{   // memory is not empty.

            int stringIntervalIndex = 0;
            int tempStart;
            int endOfLast = -1;    //  This will be the index of the last character of the previous interval.
            //  The next gap will start at endOfLast+1.
            boolean found = false;

            /*
             * Step through the string interval list and, for each string interval,
             * check if there is a gap *before* that interval.
             * The gap size is the start of the current interval minus endOfLast.
             */

            for (Memory.StringInterval tempInterval : intervalList) {
                tempStart = tempInterval.start;
                if (endOfLast + len < tempStart) {  //  we've found a viable gap before the start of tempInterval.
                    //   tempStart will be the starting location
                    found = true;
                    break;
                }
                else {   //  the gap is not big enough for the current string.  So move on to the next one.
                    //  end of last will be the last index of the current interval
                    endOfLast = tempInterval.start + tempInterval.length - 1;
                    stringIntervalIndex++;
                }
            }
            /*
             *   If there is no room before an interval, then there may still be room after the last interval.
             *   Note that that stringIntervalIndex will be one greater than the list index of the final string,
             *   and so it has the correct value for inserting a new string interval.
             */

            if (!found) {   // endOfLast + len would be the index of last char, in case there is room *after* the last string interval.
                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                };
            }

            if (!found) {  //  If there was no room found, then defragment and check if there is room at the end of the array
                defragment();   //  after this method runs,  all intervals have been shifted and the only gap is at the end.
                //  so check if this gap is big enough for the new string
                Memory.StringInterval interval = intervalList.getLast();
                endOfLast =  interval.start + interval.length - 1;

                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                }
            }

            /*
             * All possible ways of finding a gap have been tested. If we've found a gap,
             * then we can
             * - insert the characters
             * - increment the idCount
             * - add a string interval object to the list at the appropriate place
             */

            if (found) {
                //   write the characters in memory
                for (int i = 0; i < len; i++) {
                    memoryArray[endOfLast + 1 + i] = s.charAt(i);
                }
                //   add a new interval to the list
                idCount++;
                Memory.StringInterval interval = new Memory.StringInterval(idCount, endOfLast+1, s.length());

                /*
                 * By using the Java LinkedList class, students can avoid the headaches of implementing their own (and getting it wrong).
                 * The interval will be added before the current interval if a gap was found before some string interval,
                 * or else it will be added after the last interval.
                 */

                intervalList.add(stringIntervalIndex,interval);

                return idCount;
            }
        }
        return -1;
    }
}

class Solution_Defragment extends Memory {

    public char[]  memoryArray;
    public static int idCount = 0;   //  increment this whenever we create a new Interval

    public Memory.StringInterval interval;
    public LinkedList<Memory.StringInterval>  intervalList = new LinkedList<Memory.StringInterval>();

    public Solution_Defragment(int n) {
        super(n);
        idCount = 0;
        memoryArray = super.memoryArray;
        intervalList = super.intervalList;
    }

    public String get(int id) {
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s = s + memoryArray[tempInterval.start + i];
                }
                return s;
            }
        }
        return null;
    }

    public int get(String s) {
        String sm;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                return tempInterval.id;
            }
        }
        return -1;
    }

    public int remove(String s) {
        String sm;
        int index = 0;
        int id = -1;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                id = tempInterval.id;
                break;
            }
            else
                index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
        }
        return id;
    }

    public String remove(int id) {

        //  Maybe rewrite this one to return -1 if the id doesn't exist and 1 if it does.
        //  Or return the string?

        int index = 0;
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s += memoryArray[tempInterval.start + i];
                }
                break;
            }
            index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
            return s;

        }
        return null;
    }

    //  Normally we would make this private.  But for grading purposes, we can make it public.

//    public void defragment() {
//        int memoryIndex = 0;
//        for (Memory.StringInterval tempInterval : intervalList) {
//            for (int i=0; i < tempInterval.length; i++)
//                memoryArray[memoryIndex + i] = memoryArray[tempInterval.start + i];
//            tempInterval.start = memoryIndex;
//            memoryIndex += tempInterval.length;
//        }
//    }

    public int put(String s) {

        //  First check if there are any strings in memory.  If yes, then we can add the string.
        //  Here I don't bother checking that the string can fit into empty memory, but I could add that.

        int len = s.length();
        if  (intervalList.size() == 0) {  //  no string intervals in memory
            if (len <= memoryArray.length){
                interval = new Memory.StringInterval(idCount, 0, s.length());
                intervalList.add(interval);
                for (int i = 0; i < len; i++) {
                    memoryArray[i] = s.charAt(i);
                }
                return idCount;
            }
        }
        else{   // memory is not empty.

            int stringIntervalIndex = 0;
            int tempStart;
            int endOfLast = -1;    //  This will be the index of the last character of the previous interval.
            //  The next gap will start at endOfLast+1.
            boolean found = false;

            /*
             * Step through the string interval list and, for each string interval,
             * check if there is a gap *before* that interval.
             * The gap size is the start of the current interval minus endOfLast.
             */

            for (Memory.StringInterval tempInterval : intervalList) {
                tempStart = tempInterval.start;
                if (endOfLast + len < tempStart) {  //  we've found a viable gap before the start of tempInterval.
                    //   tempStart will be the starting location
                    found = true;
                    break;
                }
                else {   //  the gap is not big enough for the current string.  So move on to the next one.
                    //  end of last will be the last index of the current interval
                    endOfLast = tempInterval.start + tempInterval.length - 1;
                    stringIntervalIndex++;
                }
            }
            /*
             *   If there is no room before an interval, then there may still be room after the last interval.
             *   Note that that stringIntervalIndex will be one greater than the list index of the final string,
             *   and so it has the correct value for inserting a new string interval.
             */

            if (!found) {   // endOfLast + len would be the index of last char, in case there is room *after* the last string interval.
                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                };
            }

            if (!found) {  //  If there was no room found, then defragment and check if there is room at the end of the array
                defragment();   //  after this method runs,  all intervals have been shifted and the only gap is at the end.
                //  so check if this gap is big enough for the new string
                Memory.StringInterval interval = intervalList.getLast();
                endOfLast =  interval.start + interval.length - 1;

                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                }
            }

            /*
             * All possible ways of finding a gap have been tested. If we've found a gap,
             * then we can
             * - insert the characters
             * - increment the idCount
             * - add a string interval object to the list at the appropriate place
             */

            if (found) {
                //   write the characters in memory
                for (int i = 0; i < len; i++) {
                    memoryArray[endOfLast + 1 + i] = s.charAt(i);
                }
                //   add a new interval to the list
                idCount++;
                interval = new Memory.StringInterval(idCount, endOfLast+1, s.length());

                /*
                 * By using the Java LinkedList class, students can avoid the headaches of implementing their own (and getting it wrong).
                 * The interval will be added before the current interval if a gap was found before some string interval,
                 * or else it will be added after the last interval.
                 */

                intervalList.add(stringIntervalIndex,interval);

                return idCount;
            }
        }
        return -1;
    }

    public String toString() {
        return new String(memoryArray);
    }


    public class StringInterval {
        int id;
        int start;
        int length;

        StringInterval(int id, int start, int length){
            this.id = id;
            this.start = start;
            this.length = length;
        }
    }

}
class Solution_Put extends Memory {


    public char[]  memoryArray;
    public static int idCount = 0;   //  increment this whenever we create a new Interval

    public Memory.StringInterval interval;
    public LinkedList<Memory.StringInterval>  intervalList = new LinkedList<Memory.StringInterval>();

    public Solution_Put(int n) {
        super(n);
        idCount = 0;
        memoryArray = super.memoryArray;
        intervalList = super.intervalList;
    }

    public String get(int id) {
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s = s + memoryArray[tempInterval.start + i];
                }
                return s;
            }
        }
        return null;
    }

    public int get(String s) {
        String sm;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                return tempInterval.id;
            }
        }
        return -1;
    }

    public int remove(String s) {
        String sm;
        int index = 0;
        int id = -1;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                id = tempInterval.id;
                break;
            }
            else
                index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
        }
        return id;
    }

    public String remove(int id) {

        //  Maybe rewrite this one to return -1 if the id doesn't exist and 1 if it does.
        //  Or return the string?

        int index = 0;
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s += memoryArray[tempInterval.start + i];
                }
                break;
            }
            index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
            return s;

        }
        return null;
    }

    //  Normally we would make this private.  But for grading purposes, we can make it public.

    public void defragment() {
        int memoryIndex = 0;
        for (Memory.StringInterval tempInterval : intervalList) {
            for (int i=0; i < tempInterval.length; i++)
                memoryArray[memoryIndex + i] = memoryArray[tempInterval.start + i];
            tempInterval.start = memoryIndex;
            memoryIndex += tempInterval.length;
        }
    }


    public String toString() {
        return new String(memoryArray);
    }


    public class StringInterval {
        int id;
        int start;
        int length;

        StringInterval(int id, int start, int length){
            this.id = id;
            this.start = start;
            this.length = length;
        }
    }

}
class Solution_RemoveS extends Memory {


    public char[]  memoryArray;
    public static int idCount = 0;   //  increment this whenever we create a new Interval

    public Memory.StringInterval interval;
    public LinkedList<Memory.StringInterval>  intervalList = new LinkedList<Memory.StringInterval>();

    public Solution_RemoveS(int n) {
        super(n);
        idCount = 0;
        memoryArray = super.memoryArray;
        intervalList = super.intervalList;
    }

    public String get(int id) {
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s = s + memoryArray[tempInterval.start + i];
                }
                return s;
            }
        }
        return null;
    }

    public int get(String s) {
        String sm;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                return tempInterval.id;
            }
        }
        return -1;
    }

//        public int remove(String s) {
//            String sm;
//            int index = 0;
//            int id = -1;
//            for (Memory.StringInterval tempInterval : intervalList) {
//                sm = "";
//                for (int i = 0; i < tempInterval.length; i++) {
//                    sm = sm + memoryArray[tempInterval.start + i];
//                }
//                if (s.equals(sm)){
//                    id = tempInterval.id;
//                    break;
//                }
//                else
//                    index++;
//            }
//            if (index < intervalList.size()) {
//                intervalList.remove(index);
//            }
//            return id;
//        }

    public String remove(int id) {

        //  Maybe rewrite this one to return -1 if the id doesn't exist and 1 if it does.
        //  Or return the string?

        int index = 0;
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s += memoryArray[tempInterval.start + i];
                }
                break;
            }
            index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
            return s;

        }
        return null;
    }

    //  Normally we would make this private.  But for grading purposes, we can make it public.

    public void defragment() {
        int memoryIndex = 0;
        for (Memory.StringInterval tempInterval : intervalList) {
            for (int i=0; i < tempInterval.length; i++)
                memoryArray[memoryIndex + i] = memoryArray[tempInterval.start + i];
            tempInterval.start = memoryIndex;
            memoryIndex += tempInterval.length;
        }
    }

    public int put(String s) {

        //  First check if there are any strings in memory.  If yes, then we can add the string.
        //  Here I don't bother checking that the string can fit into empty memory, but I could add that.

        int len = s.length();
        if  (intervalList.size() == 0) {  //  no string intervals in memory
            if (len <= memoryArray.length){
                interval = new Memory.StringInterval(idCount, 0, s.length());
                intervalList.add(interval);
                for (int i = 0; i < len; i++) {
                    memoryArray[i] = s.charAt(i);
                }
                return idCount;
            }
        }
        else{   // memory is not empty.

            int stringIntervalIndex = 0;
            int tempStart;
            int endOfLast = -1;    //  This will be the index of the last character of the previous interval.
            //  The next gap will start at endOfLast+1.
            boolean found = false;

            /*
             * Step through the string interval list and, for each string interval,
             * check if there is a gap *before* that interval.
             * The gap size is the start of the current interval minus endOfLast.
             */

            for (Memory.StringInterval tempInterval : intervalList) {
                tempStart = tempInterval.start;
                if (endOfLast + len < tempStart) {  //  we've found a viable gap before the start of tempInterval.
                    //   tempStart will be the starting location
                    found = true;
                    break;
                }
                else {   //  the gap is not big enough for the current string.  So move on to the next one.
                    //  end of last will be the last index of the current interval
                    endOfLast = tempInterval.start + tempInterval.length - 1;
                    stringIntervalIndex++;
                }
            }
            /*
             *   If there is no room before an interval, then there may still be room after the last interval.
             *   Note that that stringIntervalIndex will be one greater than the list index of the final string,
             *   and so it has the correct value for inserting a new string interval.
             */

            if (!found) {   // endOfLast + len would be the index of last char, in case there is room *after* the last string interval.
                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                };
            }

            if (!found) {  //  If there was no room found, then defragment and check if there is room at the end of the array
                defragment();   //  after this method runs,  all intervals have been shifted and the only gap is at the end.
                //  so check if this gap is big enough for the new string
                Memory.StringInterval interval = intervalList.getLast();
                endOfLast =  interval.start + interval.length - 1;

                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                }
            }

            /*
             * All possible ways of finding a gap have been tested. If we've found a gap,
             * then we can
             * - insert the characters
             * - increment the idCount
             * - add a string interval object to the list at the appropriate place
             */

            if (found) {
                //   write the characters in memory
                for (int i = 0; i < len; i++) {
                    memoryArray[endOfLast + 1 + i] = s.charAt(i);
                }
                //   add a new interval to the list
                idCount++;
                interval = new Memory.StringInterval(idCount, endOfLast+1, s.length());

                /*
                 * By using the Java LinkedList class, students can avoid the headaches of implementing their own (and getting it wrong).
                 * The interval will be added before the current interval if a gap was found before some string interval,
                 * or else it will be added after the last interval.
                 */

                intervalList.add(stringIntervalIndex,interval);

                return idCount;
            }
        }
        return -1;
    }

    public String toString() {
        return new String(memoryArray);
    }


    public class StringInterval {
        int id;
        int start;
        int length;

        StringInterval(int id, int start, int length){
            this.id = id;
            this.start = start;
            this.length = length;
        }
    }
}

class Solution_GetS extends Memory {

    public Solution_GetS(int n) {
        super(n);
        idCount = 0;
    }


    public String get(int id) {
        String s = "";
        for (StringInterval  tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s = s + memoryArray[tempInterval.start + i];
                }
                return s;
            }
        }
        return null;
    }


    public int remove(String s) {
        String sm;
        int index = 0;
        int id = -1;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                id = tempInterval.id;
                break;
            }
            else
                index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
        }
        return id;
    }

    public String remove(int id) {

        //  Maybe rewrite this one to return -1 if the id doesn't exist and 1 if it does.
        //  Or return the string?

        int index = 0;
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s += memoryArray[tempInterval.start + i];
                }
                break;
            }
            index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
            return s;

        }
        return null;
    }

    //  Normally we would make this private.  But for grading purposes, we can make it public.

    public void defragment() {
        int memoryIndex = 0;
        for (Memory.StringInterval tempInterval : intervalList) {
            for (int i=0; i < tempInterval.length; i++)
                memoryArray[memoryIndex + i] = memoryArray[tempInterval.start + i];
            tempInterval.start = memoryIndex;
            memoryIndex += tempInterval.length;
        }
    }

    public int put(String s) {

        //  First check if there are any strings in memory.  If yes, then we can add the string.
        //  Here I don't bother checking that the string can fit into empty memory, but I could add that.

        int len = s.length();
        if  (intervalList.size() == 0) {  //  no string intervals in memory
            if (len <= memoryArray.length){
                Memory.StringInterval interval = new Memory.StringInterval(idCount, 0, s.length());
                intervalList.add(interval);
                for (int i = 0; i < len; i++) {
                    memoryArray[i] = s.charAt(i);
                }
                return idCount;
            }
        }
        else{   // memory is not empty.

            int stringIntervalIndex = 0;
            int tempStart;
            int endOfLast = -1;    //  This will be the index of the last character of the previous interval.
            //  The next gap will start at endOfLast+1.
            boolean found = false;

            /*
             * Step through the string interval list and, for each string interval,
             * check if there is a gap *before* that interval.
             * The gap size is the start of the current interval minus endOfLast.
             */

            for (Memory.StringInterval tempInterval : intervalList) {
                tempStart = tempInterval.start;
                if (endOfLast + len < tempStart) {  //  we've found a viable gap before the start of tempInterval.
                    //   tempStart will be the starting location
                    found = true;
                    break;
                }
                else {   //  the gap is not big enough for the current string.  So move on to the next one.
                    //  end of last will be the last index of the current interval
                    endOfLast = tempInterval.start + tempInterval.length - 1;
                    stringIntervalIndex++;
                }
            }
            /*
             *   If there is no room before an interval, then there may still be room after the last interval.
             *   Note that that stringIntervalIndex will be one greater than the list index of the final string,
             *   and so it has the correct value for inserting a new string interval.
             */

            if (!found) {   // endOfLast + len would be the index of last char, in case there is room *after* the last string interval.
                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                };
            }

            if (!found) {  //  If there was no room found, then defragment and check if there is room at the end of the array
                defragment();   //  after this method runs,  all intervals have been shifted and the only gap is at the end.
                //  so check if this gap is big enough for the new string
                Memory.StringInterval interval = intervalList.getLast();
                endOfLast =  interval.start + interval.length - 1;

                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                }
            }

            /*
             * All possible ways of finding a gap have been tested. If we've found a gap,
             * then we can
             * - insert the characters
             * - increment the idCount
             * - add a string interval object to the list at the appropriate place
             */

            if (found) {
                //   write the characters in memory
                for (int i = 0; i < len; i++) {
                    memoryArray[endOfLast + 1 + i] = s.charAt(i);
                }
                //   add a new interval to the list
                idCount++;
                Memory.StringInterval interval = new Memory.StringInterval(idCount, endOfLast+1, s.length());

                /*
                 * By using the Java LinkedList class, students can avoid the headaches of implementing their own (and getting it wrong).
                 * The interval will be added before the current interval if a gap was found before some string interval,
                 * or else it will be added after the last interval.
                 */

                intervalList.add(stringIntervalIndex,interval);

                return idCount;
            }
        }
        return -1;
    }
}


class Solution_Memory extends Memory {

    public Solution_Memory(int n) {
        super(n);
        idCount = 0;
    }


    public String get(int id) {
        String s = "";
        for (StringInterval  tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s = s + memoryArray[tempInterval.start + i];
                }
                return s;
            }
        }
        return null;
    }


    public int remove(String s) {
        String sm;
        int index = 0;
        int id = -1;
        for (Memory.StringInterval tempInterval : intervalList) {
            sm = "";
            for (int i = 0; i < tempInterval.length; i++) {
                sm = sm + memoryArray[tempInterval.start + i];
            }
            if (s.equals(sm)){
                id = tempInterval.id;
                break;
            }
            else
                index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
        }
        return id;
    }

    public String remove(int id) {

        //  Maybe rewrite this one to return -1 if the id doesn't exist and 1 if it does.
        //  Or return the string?

        int index = 0;
        String s = "";
        for (Memory.StringInterval tempInterval : intervalList) {
            if (tempInterval.id == id) {
                for (int i = 0; i < tempInterval.length; i++) {
                    s += memoryArray[tempInterval.start + i];
                }
                break;
            }
            index++;
        }
        if (index < intervalList.size()) {
            intervalList.remove(index);
            return s;

        }
        return null;
    }

    //  Normally we would make this private.  But for grading purposes, we can make it public.

    public void defragment() {
        int memoryIndex = 0;
        for (Memory.StringInterval tempInterval : intervalList) {
            for (int i=0; i < tempInterval.length; i++)
                memoryArray[memoryIndex + i] = memoryArray[tempInterval.start + i];
            tempInterval.start = memoryIndex;
            memoryIndex += tempInterval.length;
        }
    }

    public int put(String s) {

        //  First check if there are any strings in memory.  If yes, then we can add the string.
        //  Here I don't bother checking that the string can fit into empty memory, but I could add that.

        int len = s.length();
        if  (intervalList.size() == 0) {  //  no string intervals in memory
            if (len <= memoryArray.length){
                Memory.StringInterval interval = new Memory.StringInterval(idCount, 0, s.length());
                intervalList.add(interval);
                for (int i = 0; i < len; i++) {
                    memoryArray[i] = s.charAt(i);
                }
                return idCount;
            }
        }
        else{   // memory is not empty.

            int stringIntervalIndex = 0;
            int tempStart;
            int endOfLast = -1;    //  This will be the index of the last character of the previous interval.
            //  The next gap will start at endOfLast+1.
            boolean found = false;

            /*
             * Step through the string interval list and, for each string interval,
             * check if there is a gap *before* that interval.
             * The gap size is the start of the current interval minus endOfLast.
             */

            for (Memory.StringInterval tempInterval : intervalList) {
                tempStart = tempInterval.start;
                if (endOfLast + len < tempStart) {  //  we've found a viable gap before the start of tempInterval.
                    //   tempStart will be the starting location
                    found = true;
                    break;
                }
                else {   //  the gap is not big enough for the current string.  So move on to the next one.
                    //  end of last will be the last index of the current interval
                    endOfLast = tempInterval.start + tempInterval.length - 1;
                    stringIntervalIndex++;
                }
            }
            /*
             *   If there is no room before an interval, then there may still be room after the last interval.
             *   Note that that stringIntervalIndex will be one greater than the list index of the final string,
             *   and so it has the correct value for inserting a new string interval.
             */

            if (!found) {   // endOfLast + len would be the index of last char, in case there is room *after* the last string interval.
                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                };
            }

            if (!found) {  //  If there was no room found, then defragment and check if there is room at the end of the array
                defragment();   //  after this method runs,  all intervals have been shifted and the only gap is at the end.
                //  so check if this gap is big enough for the new string
                Memory.StringInterval interval = intervalList.getLast();
                endOfLast =  interval.start + interval.length - 1;

                if ( endOfLast + len < memoryArray.length ) {
                    found = true;
                }
            }

            /*
             * All possible ways of finding a gap have been tested. If we've found a gap,
             * then we can
             * - insert the characters
             * - increment the idCount
             * - add a string interval object to the list at the appropriate place
             */

            if (found) {
                //   write the characters in memory
                for (int i = 0; i < len; i++) {
                    memoryArray[endOfLast + 1 + i] = s.charAt(i);
                }
                //   add a new interval to the list
                idCount++;
                Memory.StringInterval interval = new Memory.StringInterval(idCount, endOfLast+1, s.length());

                /*
                 * By using the Java LinkedList class, students can avoid the headaches of implementing their own (and getting it wrong).
                 * The interval will be added before the current interval if a gap was found before some string interval,
                 * or else it will be added after the last interval.
                 */

                intervalList.add(stringIntervalIndex,interval);

                return idCount;
            }
        }
        return -1;
    }
}

public class TestMain {
    Solution_Defragment sd;

    @Test
    @Tag("hidden")
    @Tag("score:4")
    @DisplayName("Defragment Test 1")
    void testDefragment1(){
        sd = new Solution_Defragment(35);

        sd.put("kiwi");
        sd.put("apple");
        sd.put("nuts");
        sd.put("mango");
        sd.put("pear");
        sd.put("melon");
        sd.put("orange");

        sd.remove("nuts");

        try{
            assertEquals(7, sd.put("banana"));
        } catch (AssertionError e) {
            Memory sd = new Memory(35);

            sd.put("kiwi");
            sd.put("apple");
            sd.put("nuts");
            sd.put("mango");
            sd.put("pear");
            sd.put("melon");
            sd.put("orange");

            sd.remove("nuts");

            assertEquals(7, sd.put("banana"));


        }


    }

    @Test
    @Tag("hidden")
    @Tag("score:4")
    @DisplayName("Defragment Test 2")
    void testDefragment2(){
        sd = new Solution_Defragment(11);

        sd.put("an");
        sd.put("apple");
        sd.put("a");
        sd.put("day");


        sd.remove("a");
        sd.remove("an");

        try{
            assertEquals(4, sd.put("tea"));
        } catch (AssertionError e) {
            Memory sd = new Memory(11);

            sd.put("an");
            sd.put("apple");
            sd.put("a");
            sd.put("day");


            sd.remove("a");
            sd.remove("an");


            assertEquals(4, sd.put("tea"));

        }

    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Defragment Test 3")
    void testDefragment3(){
        sd = new Solution_Defragment(11);

        sd.put("an");
        sd.put("apple");
        sd.put("a");
        sd.put("day");


        sd.remove("an");
        sd.remove("day");



        try{
            assertEquals(4, sd.put("mango"));
        } catch (AssertionError e) {
            Memory sd = new Memory(11);

            sd.put("an");
            sd.put("apple");
            sd.put("a");
            sd.put("day");


            sd.remove("an");
            sd.remove("day");


            assertEquals(4, sd.put("mango"));

        }

    }

    static Sol_Get_Int memory;

    @Test
    @Tag("score:2")
    @DisplayName("Get(int) Test 1")
    void gtest1() {
        memory.put("string");

        try{
            assertEquals("string", memory.get(0));
        } catch (AssertionError e) {
            Memory memory = new Memory(25);

            memory.put("string");
            assertEquals("string", memory.get(0));

        }
    }

    @Test
    @Tag("score:2")
    @DisplayName("Get(int) Test 2")
    void gtest2() {
        memory.put("linked");
        memory.put("list");
        memory.remove("list");

        try{
            assertNull(memory.get(1));
        } catch (AssertionError e) {
            Memory memory = new Memory(25);

            memory.put("linked");
            memory.put("list");
            memory.remove("list");

            assertNull(memory.get(1));

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Get(int) Test 3")
    void gemptyMem() {

        try{
            assertNull(memory.get(0));
        } catch (AssertionError e) {
            Memory memory = new Memory(25);

            assertNull(memory.get(0));

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Get(int) Test 4")
    void gtest4() {
        memory.put("linked");
        memory.put("list");
        memory.put("is");
        memory.remove("list");
        memory.put("fun");

        try{
            assertEquals("fun", memory.get(3));
        } catch (AssertionError e) {
            Memory memory = new Memory(25);

            memory.put("linked");
            memory.put("list");
            memory.put("is");
            memory.remove("list");
            memory.put("fun");

            assertEquals("fun", memory.get(3));

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Get(int) Test 5")
    void test5() {
//        System.out.println(memory.counter);
        memory.put("comp");
        memory.put("250");
        memory.put("is");
        memory.put("so");
        memory.remove("is");
        memory.put("fun");

        try{
            assertEquals("fun", memory.get(4));
        } catch (AssertionError e) {
            Memory memory = new Memory(25);

            memory.put("comp");
            memory.put("250");
            memory.put("is");
            memory.put("so");
            memory.remove("is");
            memory.put("fun");

            assertEquals("fun", memory.get(4));

        }
    }

    Solution_Put sp;

    @Test
    @Tag("score:10")
    @DisplayName("Put Test 1")
    void testPut1(){
        sp = new Solution_Put(10);
        sp.put("kiwi");

        try{
            assertEquals(0, sp.get("kiwi"));
        } catch (AssertionError e) {
            Memory sp = new Memory(10);

            sp.put("kiwi");

            assertEquals(0, sp.get("kiwi"));

        }

    }


    @Test
    @Tag("score:10")
    @DisplayName("Put Test 2")
    void testPut2(){
        sp = new Solution_Put(10);

        sp.put("an");
        sp.put("apple");



        try{
            assertEquals(0, sp.get("an"));
            assertEquals(1, sp.get("apple"));
        } catch (AssertionError e) {
            Memory sp = new Memory(10);
            sp.put("an");
            sp.put("apple");

            assertEquals(0, sp.get("an"));
            assertEquals(1, sp.get("apple"));

        }

    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Put Test 3")
    void testPut3(){
        sp = new Solution_Put(8);

        sp.put("an");
        sp.put("apple");
        sp.put("a");

        sp.remove("apple");

        sp.put("fruit");

        try{
            assertEquals(sp.get("fruit"), 3);
        } catch (AssertionError e) {
            Memory sp = new Memory(8);

            sp.put("an");
            sp.put("apple");
            sp.put("a");

            sp.remove("apple");

            sp.put("fruit");
            assertEquals(sp.get("fruit"), 3);

        }

    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Put Test 4")
    void testPut4(){
        sp = new Solution_Put(2);



        try{
            assertEquals(sp.put("kiwi"), -1);
            assertEquals(sp.get("kiwi"), -1);
        } catch (AssertionError e) {
            Memory sp = new Memory(2);

            assertEquals(sp.put("kiwi"), -1);
            assertEquals(sp.get("kiwi"), -1);

        }

    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Put Test 5")
    void testPut5(){
        sp = new Solution_Put(35);

        sp.put("kiwi");
        sp.put("apple");
        sp.put("nuts");
        sp.put("mango");
        sp.put("pear");
        sp.put("melon");
        sp.put("orange");

        sp.remove("nuts");


        try{
            assertEquals(sp.put("banana"), 7);
            assertEquals(sp.get("banana"), 7);
        } catch (AssertionError e) {
            Memory sp = new Memory(35);


            sp.put("kiwi");
            sp.put("apple");
            sp.put("nuts");
            sp.put("mango");
            sp.put("pear");
            sp.put("melon");
            sp.put("orange");

            sp.remove("nuts");
            assertEquals(sp.put("banana"), 7);
            assertEquals(sp.get("banana"), 7);

        }

    }


    @Test
    @Tag("hidden")
    @Tag("score:5")
    @DisplayName("Put Test 6")
    void testPut6(){
        sp = new Solution_Put(33);

        sp.put("kiwi");
        sp.put("apple");
        sp.put("nuts");
        sp.put("mango");
        sp.put("pear");
        sp.put("melon");
        sp.put("orange");

        sp.remove("nuts");


        try{
            assertEquals(sp.put("banana"), -1);
            assertEquals(sp.get("banana"), -1);
        } catch (AssertionError e) {
            Memory sp = new Memory(33);


            sp.put("kiwi");
            sp.put("apple");
            sp.put("nuts");
            sp.put("mango");
            sp.put("pear");
            sp.put("melon");
            sp.put("orange");

            sp.remove("nuts");
            assertEquals(sp.put("banana"), -1);
            assertEquals(sp.get("banana"), -1);

        }

    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Put Test 7")
    void testPut7(){
        sp = new Solution_Put(33);

        sp.put("kiwi");
        sp.put("apple");
        sp.put("nuts");
        sp.put("mango");
        sp.put("pear");
        sp.put("melon");
        sp.put("orange");

        sp.remove("orange");


        try{
            assertEquals(sp.put("banana"), 7);
            assertEquals(sp.get("banana"), 7);
        } catch (AssertionError e) {
            Memory sp = new Memory(33);


            sp.put("kiwi");
            sp.put("apple");
            sp.put("nuts");
            sp.put("mango");
            sp.put("pear");
            sp.put("melon");
            sp.put("orange");

            sp.remove("orange");
            assertEquals(sp.put("banana"), 7);
            assertEquals(sp.get("banana"), 7);

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:5")
    @DisplayName("Put Test 8")
    void testPut8(){
        sp = new Solution_Put(33);

        sp.put("kiwi");
        sp.put("apple");
        sp.put("orange");
        sp.put("mango");
        sp.put("pear");
        sp.put("melon");
        sp.put("nuts");


        sp.remove("nuts");
        sp.remove("orange");
        int x = sp.put("banana");
        int y = sp.put("plum");
        sp.remove("kiwi");
        int z = sp.put("strawberry");
        int w = sp.put("sun");

        try{
            assertEquals(sp.get("banana"), 7);
            assertEquals(x, 7);
            assertEquals(sp.get("plum"), 8);
            assertEquals(y, 8);
            assertEquals(sp.get("stawberry"), -1);
            assertEquals(z, -1);
            assertEquals(sp.get("sun"), 9);
            assertEquals(w, 9);
        } catch (AssertionError e) {
            Memory sp = new Memory(33);


            sp.put("kiwi");
            sp.put("apple");
            sp.put("orange");
            sp.put("mango");
            sp.put("pear");
            sp.put("melon");
            sp.put("nuts");


            sp.remove("nuts");
            sp.remove("orange");
            x = sp.put("banana");
            y = sp.put("plum");
            sp.remove("kiwi");
            z = sp.put("strawberry");
            w = sp.put("sun");


            assertEquals(sp.get("banana"), 7);
            assertEquals(x, 7);
            assertEquals(sp.get("plum"), 8);
            assertEquals(y, 8);
            assertEquals(sp.get("stawberry"), -1);
            assertEquals(z, -1);
            assertEquals(sp.get("sun"), 9);
            assertEquals(w, 9);

        }

    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Put Test 9")
    void testPut9(){
        sp = new Solution_Put(33);

        sp.put("kiwi");
        sp.put("apple");
        sp.remove("kiwi");
        sp.put("nuts");
        sp.put("mango");
        sp.put("pear");
        sp.put("melon");
        sp.put("orange");

        try{
            assertEquals(sp.put("plum"), 7);
            assertEquals(sp.get("plum"), 7);
        } catch (AssertionError e) {
            Memory sp = new Memory(33);


            sp.put("kiwi");
            sp.put("apple");
            sp.remove("kiwi");
            sp.put("nuts");
            sp.put("mango");
            sp.put("pear");
            sp.put("melon");
            sp.put("orange");

            assertEquals(sp.put("plum"), 7);
            assertEquals(sp.get("plum"), 7);


        }

    }

    static Sol_Remove_Int smm;

    @Test
    @Tag("score:2")
    @DisplayName("Remove(int) Test 1")
    void test1() {
        smm.put("string");

        try{
            assertEquals("string", smm.remove(0));
        } catch (AssertionError e) {
            Memory smm = new Memory(25);

            smm.put("string");
            assertEquals("string", smm.remove(0));

        }
    }

    @Test
    @Tag("score:2")
    @DisplayName("Remove(int) Test 2")
    void test2() {
        smm.put("string");

        try{
            assertNull(memory.remove(2));
        } catch (AssertionError e) {
            Memory smm = new Memory(25);

            smm.put("string");
            assertNull(memory.remove(2));

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Remove(int) Test 3")
    void test3() {
        smm.put("matcha");
        smm.put("latte");
        smm.remove(1);

        try{
            assertNull(smm.remove(1));
        } catch (AssertionError e) {
            Memory smm = new Memory(25);

            smm.put("matcha");
            smm.put("latte");
            smm.remove(1);
            assertNull(smm.remove(1));

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Remove(int) Test 4")
    void test4() {
        smm.put("schrodinger");
        smm.put("string");

        try{
            assertNull(smm.remove(Integer.MAX_VALUE));
        } catch (AssertionError e) {
            Memory smm = new Memory(25);

            smm.put("schrodinger");
            smm.put("string");
            assertNull(smm.remove(Integer.MAX_VALUE));

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:2")
    @DisplayName("Remove(int) Test 5")
    void emptyMem() {

        try{
            assertNull(smm.remove(0));
        } catch (AssertionError e) {
            Memory smm = new Memory(25);

            assertNull(smm.remove(0));

        }
    }

    Solution_RemoveS sm;

    @BeforeEach
    void setUp(){
        sm = new Solution_RemoveS(30);
        memory = new Sol_Get_Int(25);
        smm = new Sol_Remove_Int(25);
    }

    @Test
    @Tag("score:5")
    @DisplayName("RemoveS Test 1")
    void testRemoveSE1(){
        sm.put("kiwi");
        sm.remove("kiwi");

        try{
            assertNull(sm.get(0));
        } catch (AssertionError e) {
            Memory sm = new Memory(30);

            sm.put("kiwi");
            sm.remove("kiwi");
            assertNull(sm.get(0));

        }

    }

    @Test
    @Tag("hidden")
    @Tag("score:1")
    @DisplayName("RemoveS Test 2")
    void testRemoveSE2(){
        sm.put("kiwi");
        sm.put("apple");
        sm.remove("apple");

        try{
            assertNull(sm.get(1));
        } catch (AssertionError e) {
            Memory sm = new Memory(30);

            sm.put("kiwi");
            sm.put("apple");
            sm.remove("apple");
            assertNull(sm.get(1));

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:1")
    @DisplayName("RemoveS Test 3")
    void testRemoveSE3(){
        sm.put("kiwi");
        sm.put("apple");
        sm.put("banana");
        sm.put("mango");

        try{
            Assertions.assertEquals(sm.remove("banana"),2);
        } catch (AssertionError e) {
            Memory sm = new Memory(30);

            sm.put("kiwi");
            sm.put("apple");
            sm.put("banana");
            sm.put("mango");

            Assertions.assertEquals(sm.remove("banana"),2);

        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:1")
    @DisplayName("RemoveS Test 4")
    void testRemoveSE4(){
        sm.put("kiwi");
        sm.put("apple");


        try{
            Assertions.assertEquals(sm.remove("banana"), -1);
        } catch (AssertionError e) {
            Memory sm = new Memory(30);

            sm.put("kiwi");
            sm.put("apple");

            Assertions.assertEquals(sm.remove("banana"), -1);
        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:1")
    @DisplayName("RemoveS Test 5")
    void testRemoveSE5(){
        sm.put("kiwi");
        sm.put("apple");
        sm.put("banana");
        sm.put("mango");
        sm.put("pear");
        sm.remove("banana");
        sm.remove("kiwi");

        try{
            assertEquals(sm.intervalList.size(), 3);
            assertEquals(sm.intervalList.get(0).id,1);
            assertEquals(sm.intervalList.get(1).id, 3);
            assertEquals(sm.intervalList.get(2).id, 4);
        } catch (AssertionError e) {
            Memory sm = new Memory(30);

            sm.put("kiwi");
            sm.put("apple");
            sm.put("banana");
            sm.put("mango");
            sm.put("pear");
            sm.remove("banana");
            sm.remove("kiwi");

            assertEquals(sm.intervalList.size(), 3);
            assertEquals(sm.intervalList.get(0).id,1);
            assertEquals(sm.intervalList.get(1).id, 3);
            assertEquals(sm.intervalList.get(2).id, 4);
        }
    }

    @Test
    @Tag("hidden")
    @Tag("score:1")
    @DisplayName("RemoveS Test 6")
    void testRemoveS62(){
        sm.put("kiwi");
        sm.put("apple");
        sm.put("banana");
        sm.put("mango");
        sm.put("pear");
        sm.remove("apple");
        sm.remove("banana");
        sm.remove("mango");
        sm.remove("pear");
        sm.remove("kiwi");

        try{
            assertEquals(sm.intervalList.size(), 0);
            for(int i = 0; i<5; ++i)
            {
                assertEquals(sm.get(i),null );
            }

        } catch (AssertionError e) {
            Memory sm = new Memory(30);

            sm.put("kiwi");
            sm.put("apple");
            sm.put("banana");
            sm.put("mango");
            sm.put("pear");
            sm.remove("apple");
            sm.remove("banana");
            sm.remove("mango");
            sm.remove("pear");
            sm.remove("kiwi");

            assertEquals(sm.intervalList.size(), 0);
            for(int i = 0; i<5; ++i)
            {
                assertEquals(sm.get(i),null );
            }

        }
    }

    Solution_GetS mgs;

    @Test
    @Tag("score:2")
    @DisplayName("Get(String) Test 1")
    void testGetS1(){
        mgs = new Solution_GetS(35);
        mgs.put("banana");

        try{
            assertEquals(0, mgs.get("banana"));

        } catch (AssertionError e) {
            Memory mgs = new Memory(35);

            mgs.put("banana");

            assertEquals(0, mgs.get("banana"));
        }

    }

    @Test
    @Tag("score:2")
    @DisplayName("Get(String) Test 2")
    void testGetS2(){
        mgs = new Solution_GetS(35);
        mgs.put("banana");
        mgs.put("apple");

        try{
            assertEquals(1, mgs.get("apple"));

        } catch (AssertionError e) {
            Memory mgs = new Memory(35);

            mgs.put("banana");
            mgs.put("apple");

            assertEquals(1, mgs.get("apple"));
        }
    }



    @Test
   @Tag("score:3")
   @Tag("hidden")
    @DisplayName("Get(String) Test 3")
    void testGetS3(){
        mgs = new Solution_GetS(10);
        mgs.put("apple");
        mgs.put("oranges");
    
        try{
            assertEquals(mgs.get("oranges"), -1);
        } catch (AssertionError e) {
            Memory mgs = new Memory(35);
            mgs.put("apple");
            mgs.put("oranges");
            assertEquals(mgs.get("oranges"), -1);
        }

    }
    
    @Test
 @Tag("score:3")
 @Tag("hidden")
  @DisplayName("Get(String) Test 4")
  void testGetS4(){
      mgs = new Solution_GetS(10);
        mgs.put("hello");
      mgs.put("hi");
      mgs.put("bye");
      mgs.remove("hi");
      mgs.put("no");
    
        try{
            assertEquals(mgs.get("no"), 3);
        } catch (AssertionError e) {
            Memory mgs = new Memory(35);
        mgs.put("hello");
        mgs.put("hi");
        mgs.put("bye");
        mgs.remove("hi");
        mgs.put("no");
           assertEquals(mgs.get("no"), 3);
        }

  }
 

    Memory mmc;

    @Test
    @Tag("score:2")
    @DisplayName("Memory Test 1")
    void testMemory1(){
        mmc = new Memory(35);

        try{
            assertEquals(0, mmc.idCount);

        } catch (AssertionError e) {
            Memory mgs = new Memory(35);

            assertEquals(0, mgs.idCount);
        }

    }

      
    @Test
 @Tag("score:2")
 @Tag("hidden")
  @DisplayName("Memory Test 2")
  void testMemory2(){
      mmc = new Memory(35);

      try{
        mmc.put("hello");
        mmc.put("hi");
        mmc.remove(0);
        assertEquals(0, mmc.idCount);

        } catch (AssertionError e) {
            Memory mgs = new Memory(35);
            mgs.put("hello");
            mgs.put("hi");
            mgs.remove(0);
            assertEquals(2, mgs.idCount);
        }
  }
    
    @Test
 @Tag("score:3")
  @DisplayName("Memory Test 3")
  void testMemory3(){
      mmc = new Memory(6);
      
      try{
        mmc.put("hello");
        mmc.put("hi");
        assertEquals(1, mmc.idCount);

        } catch (AssertionError e) {
            Memory mgs = new Memory(6);
            mgs.put("hello");
            mgs.put("hi");
            assertEquals(1, mgs.idCount);
        }

  }
    
    @Test
 @Tag("score:3")
 @Tag("hidden")
  @DisplayName("Memory Test 4")
  void testMemory4(){
      mmc = new Memory(8);
            try{
        mmc.put("hello");
        mmc.put("hi");
        mmc.remove(0);
        mmc.put("ciao");
        assertEquals(3, mmc.idCount);

        } catch (AssertionError e) {
            Memory mgs = new Memory(8);
            mgs.put("hello");
      mgs.put("hi");
      mgs.remove(0);
      mgs.put("ciao");
      assertEquals(3, mgs.idCount);
        }
  }
}

