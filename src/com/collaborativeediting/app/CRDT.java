package com.collaborativeediting.app;

import java.util.ArrayList;
import java.util.List;

public class CRDT {
    private List<Character> characters = new ArrayList<>();
    private int id;
    private int counter = 0;

    public CRDT(int sideId) {
        this.id = sideId;
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
            System.out.println("char: " + c.getValue() + "; position: " + c.getPosition());
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

    public void delete(int position) {
        counter++;
        this.characters.remove(position);
        printCharacters();
    }

    public void update() {
        // belum
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
    }
}
