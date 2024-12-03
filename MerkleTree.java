import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    public static String getHash(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(data.getBytes("UTF-8"));
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }

    public static String buildMerkleTree(List<String> dataBlocks) throws Exception {
        List<String> merkleTree = new ArrayList<>();

        // Hash all data blocks (leaves of the tree)
        for (String data : dataBlocks) {
            merkleTree.add(getHash(data));
        }

        // Build the tree by combining the hash pairs and hashing them
        while (merkleTree.size() > 1) {
            List<String> newLevel = new ArrayList<>();
            for (int i = 0; i < merkleTree.size(); i += 2) {
                String left = merkleTree.get(i);
                String right = (i + 1 < merkleTree.size()) ? merkleTree.get(i + 1) : left; // Handle odd case
                String combinedHash = getHash(left + right);
                newLevel.add(combinedHash);
            }
            merkleTree = newLevel;
        }

        // The root of the tree is the only element left
        return merkleTree.get(0);
    }

}