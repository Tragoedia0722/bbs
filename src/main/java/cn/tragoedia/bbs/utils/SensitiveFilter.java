package cn.tragoedia.bbs.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SensitiveFilter {
    private static final String REPLACE_STRING = "***";
    // 根节点
    private final TrieNode rootNode = new TrieNode();

    @PostConstruct
    public void init() {
        try (
                InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt")
        ) {
            assert resourceAsStream != null;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream))
            ) {
                String keyword;
                while ((keyword = reader.readLine()) != null) {
                    this.addKeyword(keyword);
                }
            }
        } catch (IOException e) {
            log.error("加载敏感词文件失败");
        }

    }

    /**
     * 过滤敏感词
     *
     * @param text 待过滤文本
     * @return 过滤文本
     */
    public String filter(String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        TrieNode tempNode = rootNode;
        int begin = 0;
        int position = 0;
        StringBuilder stringBuilder = new StringBuilder();

        while (position < text.length()) {
            char c = text.charAt(position);
            if (isSymbol(c)) {
                // 符号为根节点
                if (tempNode == rootNode) {
                    stringBuilder.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if (tempNode == null) {
                stringBuilder.append(text.charAt(begin));
                begin++;
                position = begin;
                tempNode = rootNode;
            } else if (tempNode.isKeywordEnd) {
                stringBuilder.append(REPLACE_STRING);
                position++;
                begin = position;
                tempNode = rootNode;
            } else {
                if (position < text.length() - 1) {
                    position++;
                }
            }
        }
        return stringBuilder.toString();
    }

    private boolean isSymbol(Character character) {
        // 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(character) && (character < 0x2E80 || character > 0x9FFF);
    }

    private void addKeyword(String keyword) {
        TrieNode tempNode = rootNode;
        for (int i = 0; i < keyword.length(); i++) {
            char c = keyword.charAt(i);
            TrieNode subNode = tempNode.getSubNode(c);

            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.addSubNode(c, subNode);
            }

            tempNode = subNode;

            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }

    }


    // 前缀树
    @Data
    private static class TrieNode {
        private boolean isKeywordEnd = false;
        private Map<Character, TrieNode> subNodes = new HashMap<>();

        public void addSubNode(Character character, TrieNode trieNode) {
            subNodes.put(character, trieNode);
        }

        public TrieNode getSubNode(Character character) {
            return subNodes.get(character);
        }
    }


}
