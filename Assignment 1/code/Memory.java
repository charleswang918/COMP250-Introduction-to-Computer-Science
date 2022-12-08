//Name: Chuqi Wang
//Student Number: 260842578
import java.util.LinkedList;

public class Memory{
    public class StringInterval{
        int id;
        int start;
        int length;
        public StringInterval(int id, int start, int length){
            this.id = id;
            this.start = start;
            this.length = length;
        }
    }

    public LinkedList<StringInterval> intervalList;
    public char[] memoryArray;
    public static int idCount;
    public LinkedList<StringInterval> removeList;

    public Memory(int length){
        intervalList = new LinkedList<StringInterval>();
        this.memoryArray = new char[length];
        idCount = 0;
        removeList = new LinkedList<StringInterval>();
    }
    public String get(int id){
        char [] charArray = new char[memoryArray.length];
        int count = 0;
        boolean is_valid = false;
        for (StringInterval obj:intervalList){
            if (id==obj.id){
                is_valid=true;
                for (int i=obj.start; i < obj.start+obj.length; i++){
                    charArray[i-obj.start]=memoryArray[i];
                    count++;
                }
            }
        }
        if (is_valid == true) {
            char[] wordArray = new char[count];
            for (int i = 0; i < count; i++) {
                wordArray[i] = charArray[i];
            }
            String word = new String(wordArray);
            return word;
        } else {
            return null;
        }
    }

    public int get(String s){
        String charString = new String(memoryArray);
        int startNum = charString.indexOf(s);
        int id_num = -1;
        for (StringInterval obj:intervalList){
            if (obj.start==startNum && obj.length==s.length()){
                id_num=obj.id;
            }
        }
        return id_num;
    }

    public String remove(int id){

        int startNum = -1;
        int len = 0;
        for (StringInterval obj:intervalList){
            if (obj.id==id){
                startNum = obj.start;
                len = obj.length;
                removeList.add(obj);
                sorting(removeList);
                intervalList.remove(obj);
                break;
            }
        }
        if (len!=0) {
            char[] wordArray = new char[len];
            for (int i = startNum; i < startNum + len; i++) {
                wordArray[i - startNum] = memoryArray[i];
            }
            String word = new String(wordArray);
            return word;
        } else {
            return null;
        }
    }

    public int remove(String s){
        String word = new String(memoryArray);
        int index = word.indexOf(s);
        int id_num = -1;
        if (index != -1){
            for (var obj:intervalList){
                if(obj.start==index){
                    removeList.add(obj);
                    sorting(removeList);
                    intervalList.remove(obj);
                    id_num=obj.id;
                    break;
                }
            }
            return id_num;
        } else {
            return id_num;
        }
    }

    public int put(String s) {
        boolean is_remove = false;
        int id_num = -1;
        if (intervalList.size() < idCount){
            is_remove=true;
        }
        if (is_remove==false) {
            int count = 0;
            for (char c : memoryArray) {
                if (c == '\u0000') {
                    break;
                }
                count++;
            }
            if (memoryArray.length-count >= s.length()){
                for (int i = count; i < s.length() + count; i++) {
                memoryArray[i] = s.charAt(i - count);
                }
                idCount++;
                id_num = idCount - 1;
                intervalList.add(new StringInterval(id_num, count, s.length()));
            }
            return id_num;
        }
        else {
            for (int i = 0; i < removeList.size(); i++){
                int len = removeList.get(i).length;
                int id = removeList.get(i).id;
                int start = removeList.get(i).start;
                if (len >= s.length()){
                    for(int j = start; j<start+s.length(); j++){
                        memoryArray[j]=s.charAt(j-start);
                        removeList.set(i, new StringInterval(id, start+s.length(), len-s.length()));
                    }
                    idCount++;
                    id_num = idCount - 1;
                    intervalList.add(new StringInterval(id_num, start, s.length()));
                    break;
                }
                else {
                    int count = 0;
                    for (char c : memoryArray) {
                        if (c == '\u0000') {
                            break;
                        }
                        count++;
                    }
                    if (memoryArray.length-count >= s.length()){
                        for (int j = count; j < s.length() + count; j++) {
                            memoryArray[j] = s.charAt(j - count);
                        }
                        idCount++;
                        id_num = idCount - 1;
                        intervalList.add(new StringInterval(id_num, count, s.length()));
                    }
                    else {
                        defragment();
                        int count2 = 0;
                        for (char c : memoryArray) {
                            if (c == '\u0000') {
                                break;
                            }
                            count2++;
                        }
                        if (memoryArray.length-count2 >= s.length()){
                            for (int j = count2; j < s.length() + count2; j++) {
                                memoryArray[j] = s.charAt(j - count2);
                            }
                            idCount++;
                            id_num = idCount - 1;
                            intervalList.add(new StringInterval(id_num, count, s.length()));
                        }
                        else {
                            id_num=-1;
                        }
                    }
                }
            }
            return id_num;
        }
    }



    public void defragment(){
        String[] wordArray = new String[intervalList.size()];
        for (int i = 0; i < wordArray.length; i++){
            wordArray[i] = get(intervalList.get(i).id);
        }
        for (var obj:removeList){
            for (int i = obj.start; i < obj.length+ obj.start; i++){
                memoryArray[i] = '\u0000';
            }
        }
        char[] tempArray = new char[memoryArray.length];
        for (int i = 0, j = 0; i < memoryArray.length; i++){
            if (memoryArray[i]!='\u0000'){
                tempArray[j++] = memoryArray[i];
            }
        }
        memoryArray = tempArray;
        int[] startNum = new int[wordArray.length];
        String str = new String(memoryArray);
        for (int i = 0; i < startNum.length; i++){
            startNum[i] = str.indexOf(wordArray[i]);
        }
        for (int i = 0; i < intervalList.size(); i++){
            intervalList.set(i, new StringInterval(intervalList.get(i).id, startNum[i], intervalList.get(i).length));
        }
    }

    public void sorting(LinkedList<StringInterval> l){
        for (int i = 0; i < l.size(); i++){
            for(int j = 0; j < l.size(); j++){
                if(l.get(i).start< l.get(j).start){
                    StringInterval temp = l.get(i);
                    l.set(i, l.get(j));
                    l.set(j, temp);
                }
            }
        }
    }

    public static void main(String[] args){

    }
}



