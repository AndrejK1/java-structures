package learning.examples.zipper;

import learning.tree.utils.MutableNode;
import lombok.Data;

@Data
public class ZipResult {
    private final String source;
    private final String zip;
    private final MutableNode<CharWeight> tree;
}
