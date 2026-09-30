package view;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class SwitchScreen extends JPanel {
    public SwitchScreen(Consumer<JPanel> navigate) {
        setLayout(new BorderLayout()); setBackground(UiStyle.PALE);
        JPanel root=UiStyle.root(); add(root);
        JPanel hero=new JPanel(new BorderLayout(20,0)); hero.setBackground(UiStyle.NAVY); hero.setBorder(new EmptyBorder(36,40,36,40));
        JPanel copy=new JPanel(new GridLayout(3,1,0,8)); copy.setOpaque(false); JLabel eyebrow=new JLabel("GESTÃO COMERCIAL"); eyebrow.setForeground(new Color(137,205,241)); eyebrow.setFont(new Font("SansSerif",Font.BOLD,12)); JLabel title=new JLabel("Bem-vindo ao sistema"); title.setForeground(Color.WHITE); title.setFont(new Font("SansSerif",Font.BOLD,30)); JLabel sub=new JLabel("Escolha o serviço que deseja acessar."); sub.setForeground(new Color(218,231,241)); copy.add(eyebrow);copy.add(title);copy.add(sub); hero.add(copy,BorderLayout.CENTER);
        JLabel art=new JLabel("LES.IFPE",SwingConstants.CENTER); art.setForeground(Color.WHITE); art.setFont(new Font("SansSerif",Font.BOLD,24)); art.setBorder(BorderFactory.createLineBorder(new Color(92,154,197),2)); art.setPreferredSize(new Dimension(170,110)); hero.add(art,BorderLayout.EAST); root.add(hero,BorderLayout.NORTH);
        JPanel section=new JPanel(new BorderLayout(0,14)); section.setOpaque(false); JLabel heading=new JLabel("Serviços"); heading.setFont(new Font("SansSerif",Font.BOLD,22)); heading.setForeground(UiStyle.INK); section.add(heading,BorderLayout.NORTH);
        JPanel cards=new JPanel(new GridLayout(2,2,16,16)); cards.setOpaque(false);
        addCard(cards,"Clientes","Cadastre e consulte clientes", "01",()->navigate.accept(new CustomerScreen(()->navigate.accept(this))));
        addCard(cards,"Funcionários","Gerencie a equipe", "02",()->navigate.accept(new EmployeeScreen(()->navigate.accept(this))));
        addCard(cards,"Produtos","Mantenha o catálogo atualizado", "03",()->navigate.accept(new ProductScreen(()->navigate.accept(this))));
        addCard(cards,"Vendas","Registre vendas e acompanhe o total", "04",()->navigate.accept(new SellScreen(()->navigate.accept(this))));
        section.add(cards,BorderLayout.CENTER); root.add(section,BorderLayout.CENTER);
        JLabel foot=new JLabel("Sistema de vendas • Dados armazenados localmente"); foot.setForeground(new Color(101,117,132)); root.add(foot,BorderLayout.SOUTH);
    }
    private void addCard(JPanel parent,String title,String description,String number,Runnable action) {
        JPanel card=new JPanel(new BorderLayout(10,10)); card.setBackground(Color.WHITE); card.setBorder(new EmptyBorder(18,20,18,20));
        JLabel badge=new JLabel(number); badge.setOpaque(true); badge.setBackground(new Color(222,239,250)); badge.setForeground(UiStyle.BLUE); badge.setHorizontalAlignment(SwingConstants.CENTER); badge.setFont(new Font("SansSerif",Font.BOLD,16)); badge.setPreferredSize(new Dimension(42,42)); card.add(badge,BorderLayout.WEST);
        JPanel text=new JPanel(new GridLayout(2,1,0,6)); text.setOpaque(false); JLabel name=new JLabel(title); name.setFont(new Font("SansSerif",Font.BOLD,17)); name.setForeground(UiStyle.NAVY); text.add(name); JLabel desc=new JLabel(description); desc.setForeground(new Color(100,115,130)); text.add(desc); card.add(text,BorderLayout.CENTER);
        JButton open=UiStyle.button("Abrir →",true); open.addActionListener(e->action.run()); card.add(open,BorderLayout.SOUTH); parent.add(card);
    }
}
