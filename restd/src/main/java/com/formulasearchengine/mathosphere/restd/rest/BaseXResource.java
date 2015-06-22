package com.formulasearchengine.mathosphere.restd.rest;

import com.formulasearchengine.mathosphere.basex.Client;
import com.formulasearchengine.mathosphere.restd.domain.MathRequest;
import com.formulasearchengine.mathosphere.restd.domain.MathUpdate;
import restx.Status;
import restx.annotations.DELETE;
import restx.annotations.GET;
import restx.annotations.POST;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles all REST requests to the BaseX server.
 */
@Component
@RestxResource
public class BaseXResource {
	//Log of all queries
	private List<MathRequest> queryLog = new ArrayList<>();

	@GET("/texquery")
	@PermitAll
	public MathRequest texquery( String query ) {
		final MathRequest request = new MathRequest( query ).setType( "tex" );
		queryLog.add( request );
		return request.run();
	}
	@POST("/texquery")
	@PermitAll
	public MathRequest texquery( MathRequest request ) {
		if( request.getType() == null ||  "".equals( request.getType() ) ){
			request.setType( "tex" );
		}
		queryLog.add( request );
		return request.run();
	}
	@GET("/xquery")
	@PermitAll
	public MathRequest xquery( String query ) {
		final MathRequest request = new MathRequest( query ).setType( "xquery" );
		queryLog.add( request );
		return request.run();
	}
	@POST("/xquery")
	@PermitAll
	public MathRequest xquery( MathRequest request ) {
		if( request.getType() == null ||  "".equals( request.getType() ) ){
			request.setType( "xquery" );
		}
		queryLog.add( request );
		return request.run();
	}
	@GET("/mwsquery")
	@PermitAll
	public MathRequest mwsquery( String q ) {
		final MathRequest request = new MathRequest( q ).setType( "mws" );
		queryLog.add( request );
		return request.run();
	}
	@POST("/mwsquery")
	@PermitAll
	public MathRequest mwsquery( MathRequest request ) {
		if( request.getType() == null ||  "".equals( request.getType() ) ) {
			request.setType( "mws" );
		}
		queryLog.add( request );
		return request.run();
	}
	@POST("/")
	@PermitAll
	public MathRequest query( MathRequest request ) {
		queryLog.add( request );
		return request.run();
	}
	@POST("/update")
	@PermitAll
	public MathUpdate update( MathUpdate update ) {
		return update.run();
	}
	@GET("/cntRev")
	@PermitAll
	public Integer dbsize( Integer revision ) {
		Client client = new Client();
		return client.countRevisionFormula( revision );
	}
	@GET("/cntAll")
	@PermitAll
	public Integer dbsize(  ) {
		Client client = new Client();
		return client.countAllFormula();
	}

	@DELETE("/queryLog/")
	@PermitAll
	public Status flushQueryLog() {
		queryLog.clear();
		return Status.of( "Flushed query log" );
	}

	@GET("/queryLog/")
	@PermitAll
	public List<MathRequest> getQueryLog() {
		return new ArrayList<>( queryLog );
	}
}
