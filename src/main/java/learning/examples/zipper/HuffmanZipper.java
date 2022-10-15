package learning.examples.zipper;


import learning.queue.ComparablePriorityQueue;
import learning.tree.Tree;
import learning.tree.utils.MutableNode;
import learning.tree.utils.TreeUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Just demonstration
 */
public class HuffmanZipper {

    public ZipResult zipString(String str) {
        if (str.isEmpty()) {
            return new ZipResult(str, str, TreeUtils.binaryNodeOf(new CharWeight(0, null)));
        }

        Map<Character, Integer> characterIntegerMap = toCharacterOccurrenceFrequency(str);
        ComparablePriorityQueue<MutableNode<CharWeight>> charPriorityQueue = toPriorityQueue(characterIntegerMap);
        MutableNode<CharWeight> tree = buildTree(charPriorityQueue);
        Map<Character, String> characterCodeMap = buildCodeMap(tree);
        String zip = zipString(str, characterCodeMap);

        return new ZipResult(str, zip, tree);
    }

    public String zipString(String str, Map<Character, String> characterCodeMap) {
        StringBuilder zip = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            zip.append(characterCodeMap.get(str.charAt(i)));
        }

        return zip.toString();
    }

    public String unzipString(String str, MutableNode<CharWeight> tree) {
        StringBuilder encoded = new StringBuilder();

        MutableNode<CharWeight> currentNode = tree;

        for (int i = 0; i < str.length(); i++) {
            Character currentNodeCharacter = currentNode.getValue().getCharacter();

            if (currentNodeCharacter != null) {
                encoded.append(currentNodeCharacter);
                currentNode = tree;
            }

            char encodedChar = str.charAt(i);

            if (encodedChar == '1') {
                currentNode = currentNode.getRightChild();
            } else {
                currentNode = currentNode.getLeftChild();
            }
        }

        Character currentNodeCharacter = currentNode.getValue().getCharacter();
        if (currentNodeCharacter != null) {
            encoded.append(currentNodeCharacter);
        }

        return encoded.toString();
    }

    private MutableNode<CharWeight> buildTree(ComparablePriorityQueue<MutableNode<CharWeight>> charPriorityQueue) {
        if (charPriorityQueue.size() == 1) {
            MutableNode<CharWeight> singleChar = charPriorityQueue.pop();

            return TreeUtils.binaryNodeOf(
                    new CharWeight(singleChar.getValue().getWeight(), null),
                    singleChar,
                    singleChar
            );
        }

        while (charPriorityQueue.size() > 1) {
            MutableNode<CharWeight> first = charPriorityQueue.pop();
            MutableNode<CharWeight> second = charPriorityQueue.pop();

            charPriorityQueue.push(
                    TreeUtils.binaryNodeOf(
                            new CharWeight(first.getValue().getWeight() + second.getValue().getWeight(), null),
                            first,
                            second
                    )
            );
        }

        return charPriorityQueue.pop();
    }

    private Map<Character, Integer> toCharacterOccurrenceFrequency(String str) {
        Map<Character, Integer> characterOccurrenceFrequencyMap = new HashMap<>();

        for (int i = 0; i < str.length(); i++) {
            characterOccurrenceFrequencyMap.compute(str.charAt(i), (ch, count) -> {
                if (count == null) {
                    return 1;
                }

                return ++count;
            });
        }

        return characterOccurrenceFrequencyMap;
    }

    private ComparablePriorityQueue<MutableNode<CharWeight>> toPriorityQueue(Map<Character, Integer> characterIntegerMap) {
        ComparablePriorityQueue<MutableNode<CharWeight>> charPriority = new ComparablePriorityQueue<>(Comparator.comparing(Tree.Node::getValue));
        characterIntegerMap.forEach((ch, w) -> charPriority.push(TreeUtils.binaryNodeOf(new CharWeight(w, ch))));
        return charPriority;
    }

    private Map<Character, String> buildCodeMap(MutableNode<CharWeight> tree) {
        return recursivelyParseCodeTree(tree);
    }

    private Map<Character, String> recursivelyParseCodeTree(MutableNode<CharWeight> node) {
        if (node.getValue().getCharacter() != null) {
            return Collections.singletonMap(node.getValue().getCharacter(), "");
        }

        Map<Character, String> map = new HashMap<>();

        recursivelyParseCodeTree(node.getLeftChild()).forEach((ch, code) -> map.put(ch, '0' + code));
        recursivelyParseCodeTree(node.getRightChild()).forEach((ch, code) -> map.put(ch, '1' + code));

        return map;
    }
}
