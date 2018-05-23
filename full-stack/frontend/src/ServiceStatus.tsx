import * as React from "react";
interface JavaServiceStatus {
  name: string;
  javaVersion: string;
  uptime: string;
}

interface NodeJsServiceStatus {
  name: string;
  nodeJsVersion: string;
  uptime: string;
}

interface ServiceStatusProps {
  status: JavaServiceStatus | NodeJsServiceStatus;
}

const ServiceStatus = ({ status }: ServiceStatusProps) => {
  const isJavaService = (candidate: any): candidate is JavaServiceStatus => !!candidate.javaVersion;

  return isJavaService(status) ? (
    <div>
      <b>{status.name}</b> Java {status.javaVersion} since <b>{status.uptime}</b>
    </div>
  ) : (
    <div>
      <b>{status.name}</b> NodeJS {status.nodeJsVersion} since <b>{status.uptime}</b>
    </div>
  );
};

export default ServiceStatus;
