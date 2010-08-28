package com.mastergaurav.android.mvc.command;

import java.io.InputStream;

import com.mastergaurav.android.common.xml.XMLDeserializer;
import com.mastergaurav.android.mvc.common.Response;

public class DeserializeCommand extends AbstractCommand
{

	private AbstractHttpCommand command;
	private Class<?> model;

	public DeserializeCommand(AbstractHttpCommand command, Class<?> model)
	{
		this.command = command;
		this.model = model;
	}

	@Override
	public void go()
	{
		command.execute();

		Response response = command.getResponse();
		if(!response.isError())
		{
			InputStream payload = (InputStream) response.getData();
			Object obj = XMLDeserializer.getInstance().deserialize(payload, model);

			response.setData(obj);
		}
	}
}
