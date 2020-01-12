package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;

import adapters.PipeGameMapSearchable;
import algorithms.DepthFirstSearch;
import classes.PipeGameMap;
import classes.RotateMove;
import classes.Solution;
import classes.State;

/**
 * @author Nathan Dillbary, ID 037070489 / Guy Ohayon, ID 30185171 
 * In charge of interacting with a client according to the specified protocol
 * Given a problem, this class will invoke the the implemented CacheManager for persistence operations 
 */
public class MyClientHandler implements ClientHandler {
	CacheManager<Solution<PipeGameMap>> cacheManager=new MyCacheManager<PipeGameMap>();
	Solver<PipeGameMap> solver=new MySolver<>(new DepthFirstSearch<PipeGameMap>());
	@Override
	public void handleClient(InputStream infromClient, OutputStream outtoClient) {
		BufferedReader clientInput=new BufferedReader(
				new InputStreamReader(infromClient));
		StringWriter stringWriter=new StringWriter();
		PrintWriter outToServer=new PrintWriter(stringWriter);
		PrintWriter outToClient=new PrintWriter(outtoClient);
		try {
			
			// correspond according to a well-defined protocol 
			readInputsAndSend(clientInput,outToServer,"done");
			String textfromclient=stringWriter.getBuffer().toString();
			PipeGameMap pipeGameMap=new PipeGameMap(textfromclient);
			//long time=System.currentTimeMillis();
			//System.out.println("start solution");
			PipeGameMap pipeGameMaps=getsolution(pipeGameMap);
			//System.out.println("got solution");
			Collection<RotateMove> rotateMoves = RotateMove.getRotatesMoves(pipeGameMap,
					pipeGameMaps);///may case problem see RotateMove above function getRotatesMoves
			for(RotateMove rotateMove:rotateMoves)
			{
				//System.out.println("RotateMove: "+rotateMove.toString());
				outToClient.println(rotateMove.toString());
				//System.out.println(rotateMove.toString());
				outToClient.flush(); 
			}			
			outToClient.println("done");
			outToClient.flush();
			//System.out.println("Done After : "+((System.currentTimeMillis()-time)/1000)+"."+((System.currentTimeMillis()-time)%1000)+" sec");
			clientInput.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		finally {
			outToServer.flush();
			outToServer.close();
			outToClient.close();
		}
	}
	
	private void readInputsAndSend(BufferedReader in, PrintWriter out,String exitStr)
	{
		try { 
			String line;
			while(!(line=in.readLine()).equals(exitStr))
			{
				out.println(line);
				out.flush(); 
			} 
		} 
		catch (IOException e) 
		{ 
			//e.printStackTrace();
		} 
	} 

	@SuppressWarnings({ "unchecked" })
	public PipeGameMap getsolution(PipeGameMap pipeGameMap){
		Solution<PipeGameMap> solution=null;
		PipeGameMap gameMap=null;
		boolean flag=false;
		try {
			if(cacheManager.isFileExist(pipeGameMap.hashMap().toString()))
			{
				//System.out.println("Solution Exist in Cache Manager");
				solution=cacheManager.loadFile(pipeGameMap.hashMap().toString());
				//System.out.println("Solution Loaded from Cache Manager");
				gameMap=((State<PipeGameMap>)(solution.getStates().toArray()[(solution.getStates().size()-1)])).getState();
				flag=true;
				if(!gameMap.isSolved())
				{
					//System.out.println("Error: Solution Loaded from Cache Manager is Corrapt");
					flag=false;
				}
			}
			else
			{
				//System.out.println("Solution Not Exist in Cache Manager");
			}
		}
		catch (Exception e) {
			flag=false;
			//System.out.println("Error: Solution Not Loaded from Cache Manager");
		}
		if(!flag)
		{
			
			solution = solver.solve(new PipeGameMapSearchable(pipeGameMap));
			//System.out.println("Problem Solved using the Solver");
			if(solution==null)
			{
				System.out.println("solution==null");
			}
			ArrayList<State<PipeGameMap>> states= new ArrayList<>(solution.getStates());
			State<PipeGameMap> state=states.get(states.size()-1);
			gameMap=state.getState();
			//System.out.println("gameMap:\n"+gameMap.toString());
			//System.out.println("states.size() "+states.size());
			try {
				Collection<State<PipeGameMap>> collection=new ArrayList<State<PipeGameMap>>();
				collection.add(state);
				Solution<PipeGameMap> solutiononlyone=new Solution<PipeGameMap>(collection);
				cacheManager.saveFile(pipeGameMap.hashMap().toString(),solutiononlyone);
				//System.out.println("Solution Saved in Cache Manager");
			} catch (Exception e) {
				//System.out.println("Error: Solution Not Saved in Cache Manager");
				//e.printStackTrace();
			}
			
			
		}	
		
		return gameMap;
	}
	
}
