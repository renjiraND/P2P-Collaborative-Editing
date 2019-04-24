package com.collaborativeediting.app;

import javax.smartcardio.CommandAPDU;
import java.util.ArrayList;
import java.util.List;

public class CRDT {
    private List<Character> characters = new ArrayList<>();
    private int id;
    private int counter = 0;

    public CRDT(int sideId) {
        this.id = sideId;
    }

    public List<Character> getCharacters() {
        return this.characters;
    }

    private int getCharactersCount() {
        return this.characters.size();
    }

    private Character getCharacter(int idx) {
        return this.characters.get(idx);
    }

    public String getString() {
        StringBuilder charInString = new StringBuilder(this.characters.size());
        for (Character c: characters) {
            charInString.append(c.getValue());
        }
        return charInString.toString();
    }

    public void printCharacters() {
        for (Character c: characters) {
            System.out.println("char: " + c.getValue() + "; position: " + c.getPosition()
                + "; site: " + c.getSiteId() + "; counter: " + c.getSiteCounter());
        }
        System.out.println();
    }

    public void insert(char c, int position) {
        counter++;
        if (position == getCharactersCount()) {
            this.characters.add(position, new Character(c, position+1));
        } else if (position == 0) {
            this.characters.add(position, new Character(c, getCharacter(0).getPosition() / 2));
        } else {
            double newPosition = (getCharacter(position-1).getPosition() + getCharacter(position).getPosition()) / 2;
            this.characters.add(position, new Character(c, newPosition));
        }
        printCharacters();
    }

    public double generatePos(int position) {
        if (position == getCharactersCount()) {
            return position+1;
        } else if (position == 0) {
            return getCharacter(0).getPosition() / 2;
        } else {
            return  (getCharacter(position-1).getPosition() + getCharacter(position).getPosition()) / 2;
        }
    }

    public void insertChar(Character c, int position) {
        counter++;
        this.characters.add(position, c);
        printCharacters();
    }

    public void delete(int position) {
        counter++;
        this.characters.remove(position);
        printCharacters();
    }

    public void deleteChar(Character c) {
        counter++;
        this.characters.remove(c);
        printCharacters();
    }

    public Message decode(String str) {
        String[] parsed = str.split("-");
        int type = Integer.parseInt(parsed[0]);
        int siteId = Integer.parseInt(parsed[1]);
        int siteCounter = Integer.parseInt(parsed[2]);
        char value = parsed[3].charAt(0);
        double position = Double.parseDouble(parsed[4]);
        Character c = new Character(siteId, siteCounter, value, position);
        Message msg = new Message(c, type);
        return msg;
    }

    public String encode(Message msg) {
        String strType = Integer.toString(msg.getType());
        String siteId = Integer.toString(msg.getCharacter().getSiteId());
        String siteCounter = Integer.toString(msg.getCharacter().getSiteCounter());
        String value = java.lang.Character.toString(msg.getCharacter().getValue());
        String position = Double.toString(msg.getCharacter().getPosition());
        String result = String.join("-", strType, siteId, siteCounter, value, position);
        System.out.println(result);
        return result;
    }

    public class Character {
        private int siteId;
        private int siteCounter;
        private char value;
        private double position;

        public Character(char value, double position) {
            this.siteId = id;
            this.siteCounter = counter;
            this.value = value;
            this.position = position;
        }

        public Character(int siteId, int siteCounter, char value, double position) {
            this.siteId = siteId;
            this.siteCounter = siteCounter;
            this.value = value;
            this.position = position;
        }

        public int getSiteId() {
            return siteId;
        }

        public int getSiteCounter() {
            return siteCounter;
        }

        public char getValue() {
            return value;
        }

        public double getPosition() {
            return position;
        }

        /*
         * comparison for Deletion Buffer
         * c : Character that will be deleted
         */
        public boolean isEqualTo(Character c) {
            return this.value == c.getValue()
                && this.position == c.getPosition()
                && this.siteId == c.getSiteId()
                && this.siteCounter > c.getSiteCounter();
        }
    }

    public class Message {
        private Character character;
        private int type;

        public Message(Character character, int type) {
            this.character = character;
            this.type = type;
        }

        public Character getCharacter() {
            return character;
        }

        public int getType() {
            return type;
        }
    }
}
