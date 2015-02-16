/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.poweruniverse.oim.activiti;

import java.awt.Color;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.Lane;
import org.activiti.engine.impl.pvm.process.LaneSet;
import org.activiti.engine.impl.pvm.process.ParticipantProcess;
import org.activiti.engine.impl.pvm.process.TransitionImpl;

/**
 * Class to generate an image based the diagram interchange information in a
 * BPMN 2.0 process.
 * 
 * @author Joram Barrez
 */
public class MyProcessDiagramGenerator {

  protected static final Map<String, ActivityDrawInstruction> activityDrawInstructions = new HashMap<String, ActivityDrawInstruction>();

  // The instructions on how to draw a certain construct is
  // created statically and stored in a map for performance.
  static {
    // start event
    activityDrawInstructions.put("startEvent", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawNoneStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(),color);
      }
    });

    // start timer event
    activityDrawInstructions.put("startTimerEvent", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawTimerStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });
    
    // signal catch
    activityDrawInstructions.put("intermediateSignalCatch", new ActivityDrawInstruction() {
      
      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawCatchingSignalEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });
    
    // signal throw
    activityDrawInstructions.put("intermediateSignalThrow", new ActivityDrawInstruction() {
      
      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawThrowingSignalEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });
    
    // end event
    activityDrawInstructions.put("endEvent", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawNoneEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(),color);
      }
    });

    // error end event
    activityDrawInstructions.put("errorEndEvent", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawErrorEndEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });
    
    // error end event
    activityDrawInstructions.put("terminateEvent", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawTerminateEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(),color);
      }
    });
    
    // error start event
    activityDrawInstructions.put("errorStartEvent", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawErrorStartEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });
    
    // task
    activityDrawInstructions.put("task", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });

    // user task
    activityDrawInstructions.put("userTask", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawUserTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });

    // script task
    activityDrawInstructions.put("scriptTask", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawScriptTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });

    // service task
    activityDrawInstructions.put("serviceTask", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawServiceTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });

    // receive task
    activityDrawInstructions.put("receiveTask", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawReceiveTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });

    // send task
    activityDrawInstructions.put("sendTask", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawSendTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });

    // manual task
    activityDrawInstructions.put("manualTask", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawManualTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });
    
    // businessRuleTask task
    activityDrawInstructions.put("businessRuleTask", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawBusinessRuleTask((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(),
                activityImpl.getHeight());
      }
    });

    // exclusive gateway
    activityDrawInstructions.put("exclusiveGateway", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawExclusiveGateway(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });

    // inclusive gateway
    activityDrawInstructions.put("inclusiveGateway", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawInclusiveGateway(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });

    // parallel gateway
    activityDrawInstructions.put("parallelGateway", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawParallelGateway(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight(),color);
      }
    });

    // Boundary timer
    activityDrawInstructions.put("boundaryTimer", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawCatchingTimerEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });

    // Boundary catch error
    activityDrawInstructions.put("boundaryError", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawCatchingErroEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });
    
    // Boundary signal event
    activityDrawInstructions.put("boundarySignal", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawCatchingSignalEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });

    // timer catch event
    activityDrawInstructions.put("intermediateTimer", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawCatchingTimerEvent(activityImpl.getX(), activityImpl.getY(), activityImpl.getWidth(), activityImpl.getHeight());
      }
    });

    // subprocess
    activityDrawInstructions.put("subProcess", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        Boolean isExpanded = (Boolean) activityImpl.getProperty(BpmnParse.PROPERTYNAME_ISEXPANDED);
        Boolean isTriggeredByEvent = (Boolean) activityImpl.getProperty("triggeredByEvent");
        if(isTriggeredByEvent == null) {
          isTriggeredByEvent = Boolean.TRUE;
        }
        if (isExpanded != null && isExpanded == false) {
          processDiagramCreator.drawCollapsedSubProcess((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(),
                  activityImpl.getWidth(), activityImpl.getHeight(), isTriggeredByEvent);
        } else {
          processDiagramCreator.drawExpandedSubProcess((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(),
                  activityImpl.getWidth(), activityImpl.getHeight(), isTriggeredByEvent);
        }
      }
    });

    // call activity
    activityDrawInstructions.put("callActivity", new ActivityDrawInstruction() {

      public void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color) {
        processDiagramCreator.drawCollapsedCallActivity((String) activityImpl.getProperty("name"), activityImpl.getX(), activityImpl.getY(),
                activityImpl.getWidth(), activityImpl.getHeight());
      }
    });

  }

  /**
   * Generates a PNG diagram image of the given process definition, using the
   * diagram interchange information of the process.
   */
  public static InputStream generatePngDiagram(ProcessDefinitionEntity processDefinition) {
    return generateDiagram(processDefinition, "png", Collections.<String> emptyList(),null,null);
  }

  /**
   * Generates a JPG diagram image of the given process definition, using the
   * diagram interchange information of the process.
   */
  public static InputStream generateJpgDiagram(ProcessDefinitionEntity processDefinition) {
    return generateDiagram(processDefinition, "jpg", Collections.<String> emptyList(),null,null);
  }

  protected static ProcessDiagramCanvas generateDiagram(ProcessDefinitionEntity processDefinition, List<String> todoActivities,List<String> historicActivities,List<String> liuChengLJs) {
	  MyProcessDiagramCanvas processDiagramCanvas = initProcessDiagramCanvas(processDefinition);
    
    // Draw pool shape, if process is participant in collaboration
    if(processDefinition.getParticipantProcess() != null) {
      ParticipantProcess pProc = processDefinition.getParticipantProcess();
      processDiagramCanvas.drawPoolOrLane(pProc.getName(), pProc.getX(), pProc.getY(), pProc.getWidth(), pProc.getHeight());
    }
    
    // Draw lanes
    if(processDefinition.getLaneSets() != null && processDefinition.getLaneSets().size() > 0) {
      for(LaneSet laneSet : processDefinition.getLaneSets()) {
        if(laneSet.getLanes() != null && laneSet.getLanes().size() > 0) {
          for(Lane lane : laneSet.getLanes()) {
            processDiagramCanvas.drawPoolOrLane(lane.getName(), lane.getX(), lane.getY(), lane.getWidth(), lane.getHeight());
          }
        }
      }
    }
    
    // Draw activities and their sequence-flows
    for (ActivityImpl activity : processDefinition.getActivities()) {
      drawActivity(processDiagramCanvas, activity, todoActivities,historicActivities,liuChengLJs);
    }
    return processDiagramCanvas;
  }

  public static InputStream generateDiagram(ProcessDefinitionEntity processDefinition, String imageType, List<String> todoActivities,List<String> historicActivities,List<String> liuChengLJs) {
    return generateDiagram(processDefinition, todoActivities,historicActivities,liuChengLJs).generateImage(imageType);
  }

  protected static void drawActivity(MyProcessDiagramCanvas processDiagramCanvas, ActivityImpl activity, List<String> todoActivities, List<String> finishedActivities,List<String> liuChengLJs) {
    String type = (String) activity.getProperty("type");
    ActivityDrawInstruction drawInstruction = activityDrawInstructions.get(type);
    if("endEvent".equals(type) && activity.getId().equals("Terminate")){
    	drawInstruction = activityDrawInstructions.get("terminateEvent");
    }
    if (drawInstruction != null) {
    	Color color = null;
		//开始节点和 已完成的(并行、结束节点) 绿色
		if("startEvent".equals(type) ||  (("parallelGateway".equals(type) || "endEvent".equals(type)) && finishedActivities.contains(activity.getId())) ){
			color = Color.GREEN;
		}
      drawInstruction.draw(processDiagramCanvas, activity,color);

      // Gather info on the multi instance marker
      boolean multiInstanceSequential = false, multiInstanceParallel = false, collapsed = false;
      String multiInstance = (String) activity.getProperty("multiInstance");
      if (multiInstance != null) {
        if ("sequential".equals(multiInstance)) {
          multiInstanceSequential = true;
        } else {
          multiInstanceParallel = true;
        }
      }

      // Gather info on the collapsed marker
      Boolean expanded = (Boolean) activity.getProperty(BpmnParse.PROPERTYNAME_ISEXPANDED);
      if (expanded != null) {
        collapsed = !expanded;
      }

      // Actually draw the markers
      processDiagramCanvas.drawActivityMarkers(activity.getX(), activity.getY(), activity.getWidth(), activity.getHeight(), multiInstanceSequential,
              multiInstanceParallel, collapsed);

      // Draw highlighted activities
	  if (todoActivities.contains(activity.getId())) {
		  // 待执行节点 加红色外框高亮表示 
		  //wangyang 2012-09-22
		  drawHighLight(processDiagramCanvas, activity,Color.RED);
	  }else if (finishedActivities.contains(activity.getId()) 
			  && !"startEvent".equals(type) 
			  && !"endEvent".equals(type) 
			  && !"parallelGateway".equals(type)) {
		  // 已完成节点 加绿色外框高亮表示  开始节点、并行节点、结束节点等除外(这些节点已使用绿色绘制)
		  // wangyang 2012-09-22
		  drawHighLight(processDiagramCanvas, activity,Color.GREEN);
	  }

    }

    // Outgoing transitions of activity
    for (PvmTransition sequenceFlow : activity.getOutgoingTransitions()) {
    	Color paintColor = null;
    	//原线条的颜色
    	String linecolor = (String)sequenceFlow.getProperty("linecolor");
    	try {
			paintColor = Color.decode(linecolor);
		} catch (Exception e) {
			paintColor = Color.BLACK;
		}
		//流程路径中的 线条颜色为GREEN
		if(liuChengLJs.contains(sequenceFlow.getId())){
			paintColor = Color.GREEN;
		}
		
      List<Integer> waypoints = ((TransitionImpl) sequenceFlow).getWaypoints();
      for (int i = 2; i < waypoints.size(); i += 2) { // waypoints.size()
                                                      // minimally 4: x1, y1,
                                                      // x2, y2
        boolean drawConditionalIndicator = (i == 2) && !((String) activity.getProperty("type")).toLowerCase().contains("gateway");
        if(sequenceFlow.getProperty(BpmnParse.PROPERTYNAME_CONDITION) == null || sequenceFlow.getProperty(BpmnParse.PROPERTYNAME_CONDITION) instanceof MyUelExpressionCondition){
        	drawConditionalIndicator = false;
        }
        if (i < waypoints.size() - 2) {
          processDiagramCanvas.drawSequenceflowWithoutArrow(waypoints.get(i - 2), waypoints.get(i - 1), waypoints.get(i), waypoints.get(i + 1),
                  drawConditionalIndicator,
					paintColor);
        } else {
          processDiagramCanvas.drawSequenceflow(waypoints.get(i - 2), waypoints.get(i - 1), waypoints.get(i), waypoints.get(i + 1), drawConditionalIndicator,
					paintColor);
        }
      }
    }

    // Nested activities (boundary events)
    for (ActivityImpl nestedActivity : activity.getActivities()) {
      drawActivity(processDiagramCanvas, nestedActivity, todoActivities,finishedActivities,liuChengLJs);
    }
  }
  
  //检查activity的后续路径 是否有效
//  private static boolean doCheckPath(ActivityImpl activity,PvmTransition path,List<String> finishedActivities) {
//	  for (PvmTransition sequenceFlow : activity.getOutgoingTransitions()) {
//		  //如果非当前检查分支 且分支目标为已完成 确认此分支是否通向当前检查分支的目标节点
//		  if(!path.getId().equals(sequenceFlow.getId()) && finishedActivities.contains(sequenceFlow.getDestination().getId())){
//			  boolean canPathTo = canPathTo(sequenceFlow.getDestination(),path.getDestination(),finishedActivities);
//			  if(canPathTo){
//				  //其余完成分支 有指向此目标 本路径无效
//				 return false;
//			  }
//		  }
//	  }
//	  return true;
//  }
  
  
//  private static boolean canPathTo(PvmActivity sourceActivity,PvmActivity desActivity,List<String> finishedActivities) {
//	  for (PvmTransition sequenceFlow : sourceActivity.getOutgoingTransitions()) {
//		  if(desActivity.getId().equals(sequenceFlow.getDestination().getId())){
//			  //某一分支直接指向了目标
//			  return true;
//		  }else if(finishedActivities.contains(sequenceFlow.getDestination().getId())){
//			  //未指向目标的分支 已完成 ； 递归检查
//			  boolean canPathTo = canPathTo(sequenceFlow.getDestination(),desActivity,finishedActivities);
//			  if(canPathTo){
//				 return true;
//			  }
//		  }
//	  }
//	  return false;
//  }


  private static void drawHighLight(MyProcessDiagramCanvas processDiagramCanvas, ActivityImpl activity,Color color) {
	  processDiagramCanvas.drawHighLight(activity.getX(), activity.getY(), activity.getWidth(), activity.getHeight(),color);
  }

  protected static MyProcessDiagramCanvas initProcessDiagramCanvas(ProcessDefinitionEntity processDefinition) {
    int minX = Integer.MAX_VALUE;
    int maxX = 0;
    int minY = Integer.MAX_VALUE;
    int maxY = 0;
    
    if(processDefinition.getParticipantProcess() != null) {
      ParticipantProcess pProc = processDefinition.getParticipantProcess();
      
      minX = pProc.getX();
      maxX = pProc.getX() + pProc.getWidth();
      minY = pProc.getY();
      maxY = pProc.getY() + pProc.getHeight();
    }
    
    for (ActivityImpl activity : processDefinition.getActivities()) {

      // width
      if (activity.getX() + activity.getWidth() > maxX) {
        maxX = activity.getX() + activity.getWidth();
      }
      if (activity.getX() < minX) {
        minX = activity.getX();
      }
      // height
      if (activity.getY() + activity.getHeight() > maxY) {
        maxY = activity.getY() + activity.getHeight();
      }
      if (activity.getY() < minY) {
        minY = activity.getY();
      }

      for (PvmTransition sequenceFlow : activity.getOutgoingTransitions()) {
        List<Integer> waypoints = ((TransitionImpl) sequenceFlow).getWaypoints();
        for (int i = 0; i < waypoints.size(); i += 2) {
          // width
          if (waypoints.get(i) > maxX) {
            maxX = waypoints.get(i);
          }
          if (waypoints.get(i) < minX) {
            minX = waypoints.get(i);
          }
          // height
          if (waypoints.get(i + 1) > maxY) {
            maxY = waypoints.get(i + 1);
          }
          if (waypoints.get(i + 1) < minY) {
            minY = waypoints.get(i + 1);
          }
        }
      }
    }
    
    if(processDefinition.getLaneSets() != null && processDefinition.getLaneSets().size() > 0) {
      for(LaneSet laneSet : processDefinition.getLaneSets()) {
        if(laneSet.getLanes() != null && laneSet.getLanes().size() > 0) {
          for(Lane lane : laneSet.getLanes()) {
            // width
            if (lane.getX() + lane.getWidth() > maxX) {
              maxX = lane.getX() + lane.getWidth();
            }
            if (lane.getX() < minX) {
              minX = lane.getX();
            }
            // height
            if (lane.getY() + lane.getHeight() > maxY) {
              maxY = lane.getY() + lane.getHeight();
            }
            if (lane.getY() < minY) {
              minY = lane.getY();
            }
          }
        }
      }
    }
    
    return new MyProcessDiagramCanvas(maxX + 10, maxY + 10, minX, minY);
  }

  protected interface ActivityDrawInstruction {

    void draw(MyProcessDiagramCanvas processDiagramCreator, ActivityImpl activityImpl,Color color);

  }
  
  

}
