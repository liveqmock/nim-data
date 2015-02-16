package com.poweruniverse.oim.activiti;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas;
import org.activiti.engine.impl.util.ReflectUtil;

public class MyProcessDiagramCanvas extends ProcessDiagramCanvas {
	protected static Image Circle_IMAGE;
	
	static {
	    try {
	    	Circle_IMAGE = ImageIO.read(ReflectUtil.getResourceAsStream("org/activiti/engine/impl/bpmn/parser/circle.png"));
	    } catch (IOException e) {
	      LOGGER.warning("Could not load image for process diagram creation: " + e.getMessage());
	    }
	  }
	
	public MyProcessDiagramCanvas(int width, int height) {
		super(width, height);
		Font font = new Font("宋体", Font.BOLD, 11);
		g.setFont(font);
	}
	
	public MyProcessDiagramCanvas(int width, int height, int minX, int minY) {
		super(width, height,minX,minY);
		Font font = new Font("宋体", Font.BOLD, 11);
		g.setFont(font);
	}
	
	public void drawNoneStartEvent(int x, int y, int width, int height,Color color) {
		Paint originalPaint = g.getPaint();
		g.setPaint(color);
		
		drawStartEvent(x, y, width, height, null);
		
		g.setPaint(originalPaint);
	}
	
	public void drawTerminateEvent(int x, int y, int width, int height,Color color) {
		drawNoneEndEvent(x, y, width, height,color);
	    g.drawImage(Circle_IMAGE, x + 3, y + 3, width - 6, height - 6, null);
	}



	public void drawNoneEndEvent(int x, int y, int width, int height,Color color) {
		Paint originalPaint = g.getPaint();
		g.setPaint(color);
		
		Stroke originalStroke = g.getStroke();
	    g.setStroke(END_EVENT_STROKE);
	    g.draw(new Ellipse2D.Double(x, y, width, height));
	    g.setStroke(originalStroke);
		
		g.setPaint(originalPaint);
	}

	public void drawHighLight(int x, int y, int width, int height,Color color) {
		Paint originalPaint = g.getPaint();
		Stroke originalStroke = g.getStroke();

		g.setPaint(color);
		g.setStroke(THICK_TASK_BORDER_STROKE);

		RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width, height, 20, 20);
		g.draw(rect);

		g.setPaint(originalPaint);
		g.setStroke(originalStroke);
	}
	
	public void drawSequenceflow(int srcX, int srcY, int targetX, int targetY, boolean conditional,Color color) {
		Paint originalPaint = g.getPaint();
		if(color!=null){
			g.setPaint(color);
		}
		
		Line2D.Double line = new Line2D.Double(srcX, srcY, targetX, targetY);
		g.draw(line);
		drawArrowHead(line);

		if (conditional) {
			drawConditionalSequenceFlowIndicator(line);
		}
		g.setPaint(originalPaint);
	}
	
	public void drawSequenceflowWithoutArrow(int srcX, int srcY, int targetX, int targetY, boolean conditional,Color color) {
		Paint originalPaint = g.getPaint();
		if(color!=null){
			g.setPaint(color);
		}
		
		Line2D.Double line = new Line2D.Double(srcX, srcY, targetX, targetY);
		g.draw(line);

		if (conditional) {
			drawConditionalSequenceFlowIndicator(line);
		}
		g.setPaint(originalPaint);
	}
	
	  public void drawParallelGateway(int x, int y, int width, int height, Color color) {
		  Paint originalPaint = g.getPaint();
			if(color!=null){
				g.setPaint(color);
			}
			
		    // rhombus
		    drawGateway(x, y, width, height);

		    // plus inside rhombus
		    Stroke orginalStroke = g.getStroke();
		    g.setStroke(GATEWAY_TYPE_STROKE);
		    Line2D.Double line = new Line2D.Double(x + 10, y + height / 2, x + width - 10, y + height / 2); // horizontal
		    g.draw(line);
		    line = new Line2D.Double(x + width / 2, y + height - 10, x + width / 2, y + 10); // vertical
		    g.draw(line);
		    g.setStroke(orginalStroke);
		    
		    g.setPaint(originalPaint);
		  }


}
