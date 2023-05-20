package com.sxt;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class HeadObj extends GameObj {
	
	private String direction = "right";
	
	
	
	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public HeadObj(Image img, int x, int y, GameWin frame) {
		super(img, x, y, frame);
		
		this.frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				changeDirection(e);
			}
		});
		
	}
	
	public void changeDirection(KeyEvent e) {

		switch(e.getKeyCode()) {
		case KeyEvent.VK_A:
			if (!"right".contentEquals(direction)) {
				direction = "left";
				img=GameUtils.leftImg;
			}
			break;
		case KeyEvent.VK_D:
			if (!"left".contentEquals(direction)) {
				direction = "right";
				img=GameUtils.rightImg;
			}
			break;
		case KeyEvent.VK_W:
			if (!"down".contentEquals(direction)) {
				direction = "up";
				img=GameUtils.upImg;
			}
			break;
		case KeyEvent.VK_S:
			if (!"up".contentEquals(direction)) {
				direction = "down";
				img=GameUtils.downImg;
			}
			break;
			default:
				break;
			
		}
	}
	
	public void move() {
		java.util.List<BodyObj> BodyObjList = this.frame.bodyObjList;
		for (int i=BodyObjList.size()-1; i>=1; i--) {
			BodyObjList.get(i).x=BodyObjList.get(i-1).x;
			BodyObjList.get(i).y=BodyObjList.get(i-1).y;
			if (this.x==BodyObjList.get(i).x && this.y==BodyObjList.get(i).y) {
				GameWin.state=3;
			}
		}
		BodyObjList.get(0).x=this.x;
		BodyObjList.get(0).y=this.y;
		switch (direction){
			case "up":
				y-=height;
				break;
			case "down":
				y+=height;
				break;
			case "left":
				x -= width;
				break;
			case "right":
				x += width;
				default:
					break;
		}
	}
	
	@Override
	public void paintSelf(Graphics g) {
		// TODO Auto-generated method stub
		super.paintSelf(g);
		
		FoodObj food = this.frame.foodObj;
		Integer newX=null;
		Integer newY=null;
		
		if (this.x==food.x && this.y==food.y) {
			this.frame.foodObj=food.getFood();
			BodyObj lastBody=this.frame.bodyObjList.get(this.frame.bodyObjList.size()-1);
			newX=lastBody.x;
			newY=lastBody.y;
			this.frame.score++;
		}
		
		
		if (this.frame.score>=15) {
			GameWin.state=4;
		}
		
		
		move();
		if (newX!=null && newY!=null) {
			this.frame.bodyObjList.add(new BodyObj(GameUtils.bodyImg,newX,newY,this.frame));
		}
		
		if (x<0) {
			x=570;
			
		} else if(x>570) {
			x=0;
		} else if (y<30) {
			y=570;
		} else if(y>570) {
			y=30;
		}
		
	}
	
	

}
