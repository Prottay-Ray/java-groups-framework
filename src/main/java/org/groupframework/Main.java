package org.groupframework;

import org.groupframework.group.sequence.ArraySequence;
import org.groupframework.group.Group;
import org.groupframework.group.sequence.Sequence;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        System.out.println("Java Groups Framework");
        Sequence<Integer> intGroup = new ArraySequence<>();
        for (int i = 0; i < 100; i++) {
            intGroup.add(i);
        }
        for (Integer i : intGroup) {
            intGroup.set(0, 2);
            System.out.println(i);
        }
    }
}