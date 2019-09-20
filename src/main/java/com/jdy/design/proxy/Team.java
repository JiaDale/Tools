package com.jdy.design.proxy;

import java.util.ArrayList;
import java.util.List;

/**
 * 某个歌唱表演团队
 * 1.可以表演唱歌
 *
 * @Author : Dale
 * @Create on 2018/12/29 16:28
 * @Version 1.0
 */
public class Team extends Teamer implements Sing, Dance {
    private List<Teamer> list;

    public Team() {
        System.out.println("我们是XXX表演团队！");
        init();
    }

    private void init() {
        if (null == list) {
            list = new ArrayList<>();
            list.add(new Broker());
            list.add(new Singer());
            list.add(new Dancer());
            list.add(new Perfomer());
        }
    }

    public boolean add(Teamer teamer){
        return list.add(teamer);
    }

    public boolean  remove(Teamer teamer){
        return list.remove(teamer);
    }

    public List<Teamer> getTeamers() {
        return list;
    }

    @Override
    public void sing() {
        System.out.print("唱歌 ： ");
        list.forEach(tamer -> {
            if (tamer instanceof Sing) {
                ((Sing) tamer).sing();
                System.out.print(",");
            }
        });
        System.out.println("...");
    }

    @Override
    public void dance() {
        System.out.print("舞蹈 ： ");
        list.forEach(tamer -> {
            if (tamer instanceof Dance) {
                ((Dance) tamer).dance();
                System.out.print(",");
            }
        });
        System.out.println("...");
    }

    @Override
    public void display() {
        list.forEach(Teamer::display);
    }
}
