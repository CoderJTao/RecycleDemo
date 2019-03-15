package com.example.recycledemo;

import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommonUtil {


    /**
     * 对数据进行排序
     *
     * @param list
     */
    public static void sortData(List<Hero> list) {
        if (list == null || list.size() == 0) {
            return;
        }

        // 将name首字母取出，并设置为其tag
        for (int i = 0; i < list.size(); i++) {
            Hero hero = list.get(i);

            String tag = Pinyin.toPinyin(hero.getName().toString().substring(0, 1).charAt(0)).substring(0, 1);

            if (tag.matches("[A-Z]")) {
                hero.setIndexTag(tag);
            } else {
                hero.setIndexTag("#");
            }
        }

        // 排序
        Collections.sort(list, new Comparator<Hero>() {
            @Override
            public int compare(Hero o1, Hero o2) {
                if ("#".equals(o1.getIndexTag())) {
                    return 1;
                } else if ("#".equals(o2.getIndexTag())) {
                    return -1;
                } else {
                    return o1.getIndexTag().compareTo(o2.getIndexTag());
                }
            }
        });
    }

    /**
     * 获取所有tag组成的字符串
     * @param list
     * @return
     */
    public static String getTags(List<Hero> list) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            if (!builder.toString().contains(list.get(i).getIndexTag())) {
                builder.append(list.get(i).getIndexTag());
            }
        }

        return builder.toString();
    }

}
