import * as React from "react";

interface ServiceStatus {
  name: string;
  uptime: string;
  graphiQL?: string | null;
}

interface JavaServiceStatus extends ServiceStatus {
  javaVersion: string;
}

interface NodeJsServiceStatus extends ServiceStatus {
  nodeJsVersion: string;
}

interface ServiceStatusProps {
  status: JavaServiceStatus | NodeJsServiceStatus;
}

const ServiceStatus = ({ status }: ServiceStatusProps) => {
  const isJavaService = (candidate: any): candidate is JavaServiceStatus => !!candidate.javaVersion;

  const graphiQL = status.graphiQL ? (
    <a style={{ marginLeft: "0.5rem" }} href={status.graphiQL} target="_blank">
      GraphiQL
    </a>
  ) : null;

  return isJavaService(status) ? (
    <div>
      <b>{status.name}</b> Java {status.javaVersion} since <b>{status.uptime}</b>
      {graphiQL}
    </div>
  ) : (
    <div>
      <b>{status.name}</b> NodeJS {status.nodeJsVersion} since <b>{status.uptime}</b>
      {graphiQL}
    </div>
  );
};

export default ServiceStatus;
