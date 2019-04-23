package com.collaborativeediting.app;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

//kelas ini memiliki fungsi untuk melakukan operasi terhadap CRDT baik itu insert maupun delete dan melakukan update terhadap struktur datanya
public class CRDT {
    private List<Character> characters = new ArrayList<>();
    private int id;

    public CRDT(int sideId) {
        this.id = sideId;
    }

    private int getCharactersCount() {
        return this.characters.size();
    }

    private Character getCharacter(int idx) {
        return this.characters.get(idx);
    }

    public void printCharacters() {
//        ListIterator litr = this.characters.listIterator();
//        while (litr.hasNext()) {
//            Character c = (Character) litr.next();
//            System.out.println("char: " + c.getValue() + "; position: " + c.getPosition());
//        }
        for (Character c: characters) {
            System.out.println("char: " + c.getValue() + "; position: " + c.getPosition());
        }
        System.out.println();
    }

    public void insert(char c, int position) {
        if (position == getCharactersCount()) {
            this.characters.add(position, new Character(c, position+1));
        } else if (position == 0) {
            this.characters.add(position, new Character(c, getCharacter(0).getPosition() / 2));
        } else {
            float newPosition = (getCharacter(position-1).getPosition() + getCharacter(position).getPosition()) / 2;
            this.characters.add(position, new Character(c, newPosition));
        }
        printCharacters();
    }

    public void delete(int position) {
        this.characters.remove(position);
        printCharacters();
    }

    public void update() {
        // belum
    }

    public class Character {
        private int siteId;
        private char value;
        private float position;

        public Character(char value, float position) {
            this.siteId = id;
            this.value = value;
            this.position = position;
        }

        public int getSiteId() {
            return siteId;
        }

        public char getValue() {
            return value;
        }

        public float getPosition() {
            return position;
        }
    }
}
