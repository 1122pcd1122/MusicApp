package activitytest.example.com.mymusic.ui.listen.repository;

import com.hw.lrcviewlib.LrcRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LyrBean {
    long longtime;//long类型时间
    String time, text;//时间文本与歌词文本

    public long getLongtime() {
        return longtime;
    }

    public void setLongtime(long longtime) {
        this.longtime = longtime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LyrBean(long longtime, String time, String text) {
        this.longtime = longtime;
        this.time = time;
        this.text = text;
    }

    //获取中间文本，主要是获取歌词中的歌名专辑名这些
    private static String getCenterText(String start, String end, String s) {
        if (start.isEmpty() || end.isEmpty() || s.isEmpty()) {
            return "";
        }
        int i = s.indexOf(start);
        int i1 = s.indexOf(end, i);
        if (i == -1 || i1 == -1) return "";
        String s1 = s.substring(i + start.length(), i1);
        return s1;
    }

    //解析每一行的歌词
    public static List<LrcRow> parseLine(String s) {
        if (s.isEmpty()) {
            return null;
        }
        // 去除空格
        s = s.trim();
        // 正则表达式，判断s中是否有[00:00.60]或[00:00.600]格式的片段
        Matcher lineMatcher = Pattern.
                compile("((\\[\\d{2}:\\d{2}\\.\\d{2,3}\\])+)(.+)").matcher(s);
        // 如果没有，返回null
        if (!lineMatcher.matches()) {
            return null;
        }
        // 得到时间标签
        String times = lineMatcher.group(1);
        // 得到歌词文本内容
        String text = lineMatcher.group(3);
        List<LrcRow> entryList = new ArrayList<> ();
        Matcher timeMatcher = Pattern.compile("\\[(\\d\\d):(\\d\\d)\\.(\\d{2,3})\\]").matcher(times);
        while (timeMatcher.find()) {
            long min = Long.parseLong( Objects.requireNonNull ( timeMatcher.group ( 1 ) ) );// 分
            long sec = Long.parseLong( Objects.requireNonNull ( timeMatcher.group ( 2 ) ) );// 秒
            long mil = Long.parseLong( Objects.requireNonNull ( timeMatcher.group ( 3 ) ) );// 毫秒
            // 转换为long型时间
            int scale_mil=mil>100?1:10;//如果毫秒是3位数则乘以1，反正则乘以10
            // 转换为long型时间
            long time =
                    min * 60000 +
                            sec * 1000 +
                            mil * scale_mil;
            // 最终解析得到一个list
            entryList.add(new LrcRow (text, times, time));
        }
        return entryList;
    }

}

